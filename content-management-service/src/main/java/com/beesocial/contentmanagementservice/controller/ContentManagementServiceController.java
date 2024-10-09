package com.beesocial.contentmanagementservice.controller;

import com.beesocial.contentmanagementservice.dto.ContentRequest;
import com.beesocial.contentmanagementservice.dto.ContentResponse;
import com.beesocial.contentmanagementservice.model.Content;
import com.beesocial.contentmanagementservice.service.ContentService;
import com.beesocial.contentmanagementservice.service.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
//@RequestMapping("api/content")
public class ContentManagementServiceController {

    private final ContentService contentService;
    private final ImageService imageService;
    private final EurekaDiscoveryClient discoveryClient;
    private final WebClient webClient;

    @Autowired
    public ContentManagementServiceController(ContentService contentService,
                                              ImageService imageService,
                                              EurekaDiscoveryClient discoveryClient,
                                              WebClient webClient) {
        this.contentService = contentService;
        this.imageService = imageService;
        this.discoveryClient = discoveryClient;
        this.webClient = webClient;
    }

    @GetMapping("/testContent")
    public String test() {
        return "Test from Content Management Service";
    }

    @PostMapping("/createContent")
    public ResponseEntity<?> createContent(@RequestPart("content") String contentJson) {

        ObjectMapper objectMapper = new ObjectMapper();
        ContentRequest contentRequest;

        try {
            contentRequest = objectMapper.readValue(contentJson, ContentRequest.class);
        } catch (JsonProcessingException e) {
            System.out.println(STR."Error parsing content JSON: \{e.getMessage()}");
            return ResponseEntity.badRequest().body("Invalid content data");
        }

        return ResponseEntity.ok().body(contentService.createContent(contentRequest));
    }

    @GetMapping("/getContent/{contentId}")
    public ResponseEntity<Content>getContentById(@PathVariable UUID contentId) {
        Content contentResponse = contentService.getContentById(contentId);
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
