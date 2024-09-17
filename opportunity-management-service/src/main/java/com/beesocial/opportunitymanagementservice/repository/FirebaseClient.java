package com.beesocial.opportunitymanagementservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name="firebase-storage-service")
public interface FirebaseClient {

    @PostMapping("/api/firebase/opportunity")
    String createOpportunity(@RequestBody Map<String, Object> data);

    @GetMapping("/api/firebase/opportunity/getAll")
    String getAll();

    @GetMapping("/api/firebase/opportunity/{documentId}")
    String getOpportunity(@PathVariable String documentId);

    @PutMapping("/api/firebase/opportunity/{documentId}")
    String updateOpportunity(@PathVariable("documentId") String documentId,
                             @RequestBody Map<String, Object> opportunityMap);

    @DeleteMapping("/api/firebase/opportunity/{documentId}")
    String deleteOpportunity(@PathVariable String documentId);
}
