package com.beesocial.opportunitymanagementservice.controller;

import com.beesocial.opportunitymanagementservice.dto.OpportunityRequest;
import com.beesocial.opportunitymanagementservice.service.OpportunityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("opportunity")
public class OpportunityManagementServiceController {
    private final OpportunityService opportunityService;
    private final EurekaDiscoveryClient discoveryClient;
    private final WebClient webClient;

    @Autowired
    public OpportunityManagementServiceController(OpportunityService opportunityService, EurekaDiscoveryClient discoveryClient, WebClient webClient) {
        this.opportunityService = opportunityService;
        this.discoveryClient = discoveryClient;
        this.webClient = webClient;
    }

    @GetMapping("/testEvent")
    public String test() {
        return "Test from Event Management Service";
    }

    @PostMapping("/createOpportunity")
    public ResponseEntity<String>createOpportunity(@RequestBody @Valid OpportunityRequest opportunityRequest) {
        String response = opportunityService.createOpportunity(opportunityRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllOpportunity")
    public ResponseEntity<String>getAll() {
        String response = opportunityService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getOpportunity/{documentId}")
    public ResponseEntity<String>getAll(@PathVariable String documentId) {
        String response = opportunityService.getOpportunity(documentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("updateOpportunity/{documentId}")
    public ResponseEntity<String> updateOpportunity(@PathVariable String documentId, @RequestBody @Valid OpportunityRequest opportunityRequest) {
        String response = opportunityService.updateOpportunity(documentId ,opportunityRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteOpportunity/{documentId}")
    public ResponseEntity<String> deleteOpportunity(@PathVariable String documentId) {
        String response = opportunityService.deleteOpportunity(documentId);
        return ResponseEntity.ok(response);
    }


}
