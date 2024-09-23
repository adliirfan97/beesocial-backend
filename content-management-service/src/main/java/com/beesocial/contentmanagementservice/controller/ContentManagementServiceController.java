package com.beesocial.contentmanagementservice.controller;

import com.beesocial.contentmanagementservice.dto.ContentRequest;
import com.beesocial.contentmanagementservice.service.ContentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("api/content")
public class ContentManagementServiceController {

    private final ContentService contentService;
    private final EurekaDiscoveryClient discoveryClient;
    private final WebClient webClient;

    @Autowired
    public ContentManagementServiceController(ContentService contentService,
                                              EurekaDiscoveryClient discoveryClient,
                                              WebClient webClient) {
        this.contentService = contentService;
        this.discoveryClient = discoveryClient;
        this.webClient = webClient;
    }

    @GetMapping("/testContent")
    public String test() {
        return "Test from Content Management Service";
    }

    @PostMapping("/createContent")
    public ResponseEntity<String> createContent(@RequestBody @Valid ContentRequest contentRequest) {
        String response = contentService.createContent(contentRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllContent")
    public ResponseEntity<String>getAll() {
        String response = contentService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<Void> updateContent(@RequestBody @Valid ContentRequest contentRequest) {
        //TODO: Edit Content to Database
        return null;
    }
}
