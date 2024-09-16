package com.beesocial.firebasestorageservice.service;

import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
public class CloudStorageService {

    private final String bucketName = "gs://bee-social-bb037.appspot.com";

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        StorageClient.getInstance()
                .bucket(bucketName)
                .create(fileName, file.getBytes(), file.getContentType());

        return fileName;
    }

    public URL generateDownloadUrl(String fileName) throws IOException {
        return StorageClient.getInstance()
                .bucket(bucketName)
                .get(fileName)
                .signUrl(14, TimeUnit.DAYS);
    }
}
