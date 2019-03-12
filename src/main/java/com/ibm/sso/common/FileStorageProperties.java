package com.ibm.sso.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String strDate = dateFormat.format(date);
        return uploadDir + "/" + strDate + "/";
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}