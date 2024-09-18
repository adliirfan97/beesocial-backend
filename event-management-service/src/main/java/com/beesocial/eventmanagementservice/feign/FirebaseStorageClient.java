package com.beesocial.eventmanagementservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "firebase-storage-service")
public interface FirebaseStorageClient {
    @PostMapping("/api/firebase/{collectionName}")
    public String saveData(@PathVariable String collectionName, @RequestBody ResponseEntity<Object> data);
    @PutMapping("/api/firebase/{collectionName}/{documentId}")
    public String editData(@PathVariable String collectionName, @PathVariable String documentId, @RequestBody ResponseEntity<Object> data);
    @GetMapping("/api/firebase/{collectionName}/{documentId}")
    public Map<String, Object> getData(@PathVariable String collectionName, @PathVariable String documentId);
    @GetMapping("api/firebase/{collectionName}/getAll")
    public List<Map<String, Object>> getAllData(@PathVariable String collectionName);
}
