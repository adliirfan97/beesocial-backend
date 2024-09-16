package com.beesocial.firebasestorageservice.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.HttpMethod;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
public class CloudStorageService {

    private final String bucketName = "bee-social-bb037.appspot.com";

    // Generate a signed URL for direct file upload
    public URL generateUploadSignedUrl(String fileName, String contentType) {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(contentType)
                .build();

        //Signed URL valid for 15 minutes
        return StorageClient.getInstance()
                .bucket()
                .getStorage()
                .signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                        Storage.SignUrlOption.withContentType());
    }

    // Optional: Save file metadata after successful upload
    public void saveFileMetadata(String fileName, String userId, String fileType) {
        // Save metadata to a database or Firestore if needed
        // Example: saving fileName, fileType, userId, timestamp, etc.
    }

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
