package com.beesocial.opportunitymanagementservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
