package com.beesocial.firebasestorageservice.controller;

import com.beesocial.firebasestorageservice.model.User;
import com.beesocial.firebasestorageservice.service.FirestoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {


    private final FirestoreService firestoreService;

    public FirebaseController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
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

    // Firestore: Save user data
    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUserData(@RequestParam String firstName, @RequestParam String email) throws ExecutionException, InterruptedException {
        String updateTime = firestoreService.saveUserData(firstName, email);
        return ResponseEntity.ok("User data saved at: " + updateTime);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws ExecutionException, InterruptedException {
        User user = firestoreService.getUserByEmail(email);

        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}
