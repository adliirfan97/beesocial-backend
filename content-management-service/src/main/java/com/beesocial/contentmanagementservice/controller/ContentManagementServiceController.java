package com.beesocial.contentmanagementservice.controller;

import com.beesocial.contentmanagementservice.dto.ContentRequest;
import com.beesocial.contentmanagementservice.dto.ContentResponse;
import com.beesocial.contentmanagementservice.model.Content;
import com.beesocial.contentmanagementservice.service.ContentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@RestController
//@RequestMapping("api/content")
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
    public ResponseEntity<Content> createContent(@RequestBody @Valid ContentRequest contentRequest) {
        Content response = contentService.createContent(contentRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getContent/{contentId}")
    public ResponseEntity<ContentResponse>getContentById(@PathVariable UUID contentId) {
        ContentResponse contentResponse = contentService.getContentById(contentId);
        return ResponseEntity.ok(contentResponse);
    }

    @GetMapping("/getAllContent")
    public ResponseEntity<List<Content>>getAllContent() {
        List<Content> listOfContent = contentService.getAllContent();
        return ResponseEntity.ok(listOfContent);
    }

    @GetMapping("/getAllContentFromUser/{userId}")
    public ResponseEntity<List<Content>> getAllContentFromUser(@PathVariable int userId) {
        List<Content> listOfContent = contentService.getAllContentFromUser(userId);
        return ResponseEntity.ok(listOfContent);
    }

    @DeleteMapping("/deleteContent/{contentId}")
    public ResponseEntity<String> deleteContent(@PathVariable UUID contentId) {
        contentService.deleteContent(contentId);

        return ResponseEntity.ok("Content deleted successfully");
    }

//    @PutMapping()
//    public ResponseEntity<Void> updateContent(@RequestBody @Valid ContentRequest contentRequest) {
//        //TODO: Edit Content to Database
//        return null;
//    }
}
