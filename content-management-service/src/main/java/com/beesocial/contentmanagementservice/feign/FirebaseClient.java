package com.beesocial.contentmanagementservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "firebase-storage-service")
public interface FirebaseClient {

    @PostMapping("/api/firebase/content")
    String createContent(@RequestBody Map<String, Object> data);

    @GetMapping("/api/firebase/content/getAll")
    String getAll();
}
