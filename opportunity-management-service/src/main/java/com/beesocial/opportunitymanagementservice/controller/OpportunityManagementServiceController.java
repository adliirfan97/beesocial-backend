package com.beesocial.opportunitymanagementservice.controller;

import com.beesocial.opportunitymanagementservice.dto.OpportunityRequest;
import com.beesocial.opportunitymanagementservice.model.Opportunity;
import com.beesocial.opportunitymanagementservice.service.OpportunityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object>createOpportunity(@RequestBody @Valid OpportunityRequest opportunityRequest) {
        try{
            Opportunity opportunity = opportunityService.createOpportunity(opportunityRequest);
            return ResponseEntity.ok(opportunity);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid request");
        }
    }

    @GetMapping("/getAllOpportunity")
    public ResponseEntity<String>getAll() {
        String response = opportunityService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getOpportunity/{documentId}")
    public ResponseEntity<String>getAll(@PathVariable int opportunityId) {
        String response = opportunityService.getOpportunity(opportunityId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("updateOpportunity/{documentId}")
    public ResponseEntity<String> updateOpportunity(@PathVariable int opportunityId, @RequestBody @Valid OpportunityRequest opportunityRequest) {
        String response = opportunityService.updateOpportunity(opportunityId ,opportunityRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteOpportunity/{documentId}")
    public ResponseEntity<String> deleteOpportunity(@PathVariable int opportunityId) {
        String response = opportunityService.deleteOpportunity(opportunityId);
        return ResponseEntity.ok(response);
    }


}
