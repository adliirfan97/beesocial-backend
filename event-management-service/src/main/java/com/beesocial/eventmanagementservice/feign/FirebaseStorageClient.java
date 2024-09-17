package com.beesocial.eventmanagementservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "firebase-storage-service")
public interface FirebaseStorageClient {
    @PostMapping("/api/firebase/{collectionName}")
    public String saveData(@PathVariable String collectionName, @RequestBody ResponseEntity<Object> data);
    @PutMapping("/api/firebase/{collectionName}/{documentId}")
    public String editData(@PathVariable String collectionName, @PathVariable String documentId, @RequestBody ResponseEntity<Object> data);
}
