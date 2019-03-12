package com.ibm.sso.service;

import com.aef3.data.api.GenericEntityDAO;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.ibm.sso.dao.FileDao;
import com.ibm.sso.dto.FileObjectDto;
import com.ibm.sso.dto.FileUploadResponseDto;
import com.ibm.sso.model.FileObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Date;

@Service
public class FileService extends GeneralServiceImpl<FileObjectDto, FileObject, Long> {


    private final FileDao fileDao;

    private final FileStorageService fileStorageService;

    @Autowired
    public FileService(FileDao fileDao, FileStorageService fileStorageService) {
        this.fileDao = fileDao;
        this.fileStorageService = fileStorageService;
    }

    @Override
    protected GenericEntityDAO getDAO() {
        return fileDao;
    }

    @Override
    public FileObjectDto getDtoInstance() {
        return new FileObjectDto();
    }

    public Resource findResourceByFileId(long id) {
        FileObject fileObject = fileDao.findByPrimaryKey(id);
        return fileStorageService.loadFileAsResource(fileObject.getPath());
    }


    @Transactional
    public FileUploadResponseDto saveFile(@RequestParam("file") MultipartFile file,
                                          @RequestParam(required = false) String title,
                                          String accessRoleNames) throws IOException {

        String savedName = fileStorageService.storeFile(file);
        String originalFilename = file.getOriginalFilename();

        FileObjectDto dto = doMakeFile(title, savedName, accessRoleNames, originalFilename);

        FileUploadResponseDto response = new FileUploadResponseDto();
        response.setAttachId(dto.getId());
        response.setUrl(dto.getPath());
        response.setMd5(dto.getMd5());
        return response;
    }

    private FileObjectDto doMakeFile(String title, String savedName,
                                     String originalFilename,
                                     String accessRoleNames) throws IOException {
//        Date date = new Date();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = dateFormat.format(date);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(savedName)
                .toUriString();

        HashCode hash = com.google.common.io.Files
                .hash(fileStorageService.loadFileAsFile(savedName), Hashing.md5());
        String myChecksum = hash.toString()
                .toUpperCase();
        FileUploadResponseDto fileUploadResponseDto = new FileUploadResponseDto();
        fileUploadResponseDto.setMd5(myChecksum);
        fileUploadResponseDto.setUrl(fileDownloadUri);

        FileObjectDto dto = new FileObjectDto();
        dto.setTitle(title);
        dto.setPath(savedName);
        dto.setName(originalFilename);
        dto.setMd5(myChecksum);
        dto.setUploadDate(new Date());
        dto.setRoleObject(accessRoleNames);
        dto = save(dto);
        return dto;
    }
}
