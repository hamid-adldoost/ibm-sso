package com.ibm.sso.service;

import com.ibm.sso.common.BusinessExceptionCode;
import com.ibm.sso.common.DateTools;
import com.ibm.sso.common.ErrorCodeReaderUtil;
import com.ibm.sso.common.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileStorageService {

    //    private final Path fileStorageLocation;
    //
    @Value("${file.upload-dir}")
    String uploadBaseUrl;

    @Autowired
    FileStorageProperties storageProperties;
        //
        //    @Autowired
        //    public FileStorageService(FileStorageProperties fileStorageProperties) {
        //        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
        //                .toAbsolutePath().normalize();
        //
        //        try {
        //            if(!Files.exists(this.fileStorageLocation)) {
        //                Files.createDirectories(this.fileStorageLocation);
        //            }
        //        } catch (Exception ex) {
        //            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        //        }
        //    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println(file.getContentType());

        try {
            // Check if the file's name contains invalid characters
            //            if(fileName.contains("..")) {
            //                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            //            }
            // Copy file to the target location (Replacing existing file with the same name)

            UUID uuid = UUID.randomUUID();
            String randomName = uuid.toString() + fileName.substring(fileName.length()-5, fileName.length());
            Path uploadLocation = Paths.get(storageProperties.getUploadDir());
            Path targetLocation = Paths.get(storageProperties.getUploadDir() + randomName);
            try {
                if(!Files.exists(uploadLocation)) {
                    Files.createDirectories(uploadLocation);
                }
            } catch (Exception ex) {

                throw new RuntimeException(BusinessExceptionCode.COULD_NOT_CREATE_DIRECTORY.name(),ex);
            }
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String dateString = DateTools.getDateStringInYYYMMDDFormat(new Date());
            return dateString + "/" + randomName;
        } catch (IOException ex) {
            throw new RuntimeException(MessageFormat.format(ErrorCodeReaderUtil.getResourceProperity(BusinessExceptionCode.COULD_NOT_STORED_FILE_RETRY.name()),fileName));
//            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeFile(String originalFilename, InputStream inputStream) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(originalFilename);
//        System.out.println(file.getContentType());

        try {
            // Check if the file's name contains invalid characters
            //            if(fileName.contains("..")) {
            //                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            //            }
            // Copy file to the target location (Replacing existing file with the same name)

            UUID uuid = UUID.randomUUID();
            String randomName = uuid.toString() + fileName.substring(fileName.length() - 5, fileName.length());
            Path uploadLocation = Paths.get(storageProperties.getUploadDir());
            Path targetLocation = Paths.get(storageProperties.getUploadDir() + randomName);
            try {
                if (!Files.exists(uploadLocation)) {
                    Files.createDirectories(uploadLocation);
                }
            } catch (Exception ex) {
                throw new RuntimeException(BusinessExceptionCode.COULD_NOT_CREATE_DIRECTORY.name(),ex);
//                throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
            }
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String dateString = DateTools.getDateStringInYYYMMDDFormat(new Date());
            return dateString + "/" + randomName;
        } catch (IOException ex) {
            throw  new RuntimeException(BusinessExceptionCode.COULD_NOT_STORED_FILE_RETRY.name(),ex);
//            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String relativePath) {
        try {
            Path filePath = Paths.get(uploadBaseUrl + relativePath);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException(MessageFormat.format(BusinessExceptionCode.FILE_NOT_FOUND_PATH.name(),relativePath));
//                throw new RuntimeException("FileObject not found " + relativePath);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException(MessageFormat.format(BusinessExceptionCode.FILE_NOT_FOUND_PATH.name(),relativePath),ex);
            //                throw new RuntimeException("FileObject not found " + relativePath, ex);

        }
    }

    public File loadFileAsFile(String fileName) {
//        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return new File(uploadBaseUrl + fileName);
    }
}