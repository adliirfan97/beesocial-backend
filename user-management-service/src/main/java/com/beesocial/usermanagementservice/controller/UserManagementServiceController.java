package com.beesocial.usermanagementservice.controller;

import com.beesocial.usermanagementservice.feign.FirebaseStorageClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserManagementServiceController {

    private final FirebaseStorageClient firebaseStorageClient;

    public UserManagementServiceController(FirebaseStorageClient firebaseStorageClient) {
        this.firebaseStorageClient = firebaseStorageClient;
    }

    @GetMapping("/getDocument")
    public Map<String, Object> getDocument(@RequestParam String collectionName, @RequestParam String documentId) {
        return firebaseStorageClient.getDocument(collectionName, documentId);
    }
}
