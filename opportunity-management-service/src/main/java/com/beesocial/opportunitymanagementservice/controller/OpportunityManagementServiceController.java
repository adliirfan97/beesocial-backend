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

import java.util.List;

@RestController
@RequestMapping("opportunity")
public class OpportunityManagementServiceController {
    private final OpportunityService opportunityService;

    @Autowired
    public OpportunityManagementServiceController(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    @PostMapping("/createOpportunity")
    public ResponseEntity<Object> createOpportunity(@RequestBody @Valid OpportunityRequest opportunityRequest) {
        try {
            Opportunity opportunity = opportunityService.createOpportunity(opportunityRequest);
            return ResponseEntity.ok(opportunity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid request");
        }
    }

    @GetMapping("/getAllOpportunity")
    public ResponseEntity<List<Opportunity>> getAll() {
        List<Opportunity> opportunities = opportunityService.getAll();
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/getOpportunity/{opportunityId}")
    public ResponseEntity<Object> getOpportunity(@PathVariable int opportunityId) {
        try {
            Opportunity opportunity = opportunityService.getOpportunity(opportunityId);
            return ResponseEntity.ok(opportunity);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Opportunity not found");
        }
    }

    @PutMapping("/updateOpportunity/{opportunityId}")
    public ResponseEntity<Object> updateOpportunity(@PathVariable int opportunityId, @RequestBody @Valid OpportunityRequest opportunityRequest) {
        try {
            Opportunity updatedOpportunity = opportunityService.updateOpportunity(opportunityId, opportunityRequest);
            return ResponseEntity.ok(updatedOpportunity);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Opportunity not found");
        }
    }

    @DeleteMapping("/deleteOpportunity/{opportunityId}")
    public ResponseEntity<String> deleteOpportunity(@PathVariable int opportunityId) {
        try {
            opportunityService.deleteOpportunity(opportunityId);
            return ResponseEntity.ok("Opportunity deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Opportunity not found");
        }
    }
}
