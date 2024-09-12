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

    @PostMapping()
    public ResponseEntity<String>createOpportunity(@RequestBody @Valid OpportunityRequest opportunityRequest) {
        //TODO: Add a new Opportunity to Database
        return null;
    }

    @PutMapping()
    public ResponseEntity<Void> updateOpportunity(@RequestBody @Valid OpportunityRequest opportunityRequest) {
        //TODO: Edit Opportunity to Database
        return null;
    }


}
