package com.beesocial.opportunitymanagementservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name="firebase-storage-service")
public interface FirebaseClient {

    @PostMapping("/api/firebase/opportunity")
    String createOpportunity(@RequestBody Map<String, Object> data);

    @GetMapping("/api/firebase/opportunity/getAll")
    String getAll();
}
