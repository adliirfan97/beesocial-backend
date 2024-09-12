package com.beesocial.opportumitymanagementservice.controller;

import com.beesocial.opportumitymanagementservice.service.OpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
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

}
