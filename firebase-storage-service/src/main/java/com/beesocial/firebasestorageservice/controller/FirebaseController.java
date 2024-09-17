package com.beesocial.firebasestorageservice.controller;

import com.beesocial.firebasestorageservice.model.User;
import com.beesocial.firebasestorageservice.service.CloudStorageService;
import com.beesocial.firebasestorageservice.service.FirestoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {


    private final FirestoreService firestoreService;
    private final CloudStorageService cloudStorageService;

    public FirebaseController(FirestoreService firestoreService, CloudStorageService cloudStorageService) {
        this.firestoreService = firestoreService;
        this.cloudStorageService = cloudStorageService;
    }

    // Save any object to a collection (e.g., "users", "posts", "comments")
    @PostMapping("/{collectionName}")
    public ResponseEntity<String> saveData(
            @PathVariable String collectionName,
            @RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        String updateTime = firestoreService.saveData(collectionName, data);
        return ResponseEntity.ok("Data saved to " + collectionName + " at: " + updateTime);
    }

    // Retrieve any object from a collection by document ID
    @GetMapping("/{collectionName}/{documentId}")
    public ResponseEntity<Map<String, Object>> getData(
            @PathVariable String collectionName,
            @PathVariable String documentId) throws ExecutionException, InterruptedException {
        Map<String, Object> data = firestoreService.getData(collectionName, documentId);
        return data != null ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();
    }

    // Delete any object from a collection
    @DeleteMapping("/{collectionName}/{documentId}")
    public ResponseEntity<String> deleteData(
            @PathVariable String collectionName,
            @PathVariable String documentId) throws ExecutionException, InterruptedException {
        firestoreService.deleteData(collectionName, documentId);
        return ResponseEntity.ok("Data deleted from " + collectionName);
    }

    // Generate signed URL
    @PostMapping("/generate-upload-url")
    public ResponseEntity<Map<String, Object>> generateUploadUrl(@RequestParam String fileName, @RequestParam String contentType) {
        URL signedUrl = cloudStorageService.generateUploadSignedUrl(fileName, contentType);

        Map<String, Object> response = new HashMap<>();
        response.put("signedUrl", signedUrl.toString());
        response.put("fileName", fileName);
        return ResponseEntity.ok(response);
    }

    // Cloud Storage: Upload a file
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = cloudStorageService.uploadFile(file);
        return ResponseEntity.ok("File uploaded: " + fileName);
    }

    // Cloud Storage: Get download URL
    @GetMapping("/file/{fileName}")
    public ResponseEntity<String> getFileUrl(@PathVariable String fileName) throws IOException {
        URL url = cloudStorageService.generateDownloadUrl(fileName);
        return ResponseEntity.ok(url.toString());
    }

    @PutMapping("/{collectionName}/{documentId}")
    public ResponseEntity<String> editData(
            @PathVariable String collectionName,
            @PathVariable String documentId,
            @RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        String updateTime = firestoreService.editData(collectionName, documentId, data);
        return ResponseEntity.ok("Data in " + collectionName + " updated at: " + updateTime);
    }

    @GetMapping("/{collectionName}")
    public ResponseEntity<List<Map<String, Object>>> getAllData(
            @PathVariable String collectionName
    ) throws ExecutionException, InterruptedException{
        List<Map<String, Object>> data = firestoreService.getAllData(collectionName);
        return data != null ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();
    }

//    // Firestore: Save user data
//    @PostMapping("/saveUser")
//    public ResponseEntity<String> saveUserData(@RequestParam String firstName, @RequestParam String email) throws ExecutionException, InterruptedException {
//        String updateTime = firestoreService.saveUserData(firstName, email);
//        return ResponseEntity.ok("User data saved at: " + updateTime);
//    }
//
//    @GetMapping("/user/{email}")
//    public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws ExecutionException, InterruptedException {
//        User user = firestoreService.getUserByEmail(email);
//
//        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
//    }
}
