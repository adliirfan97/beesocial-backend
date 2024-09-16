package com.beesocial.usermanagementservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "firebase-storage-service")
public interface FirebaseStorageClient {

    @GetMapping("/api/firebase/{collectionName}/{documentId}")
    Map<String, Object> getDocument(@PathVariable("collectionName") String collectionName, @PathVariable("documentId") String documentId);
}
