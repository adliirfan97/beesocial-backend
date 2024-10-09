package com.beesocial.imagemanagementservice.controller;

import com.beesocial.imagemanagementservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class ImageManagementServiceController {

    private final ImageService imageService;

    @Autowired
    public ImageManagementServiceController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/testImage")
    public String test() {
        return "Test from Image Management Service";
    }

    @GetMapping("/getImage")
    public ResponseEntity<?> getImage(@RequestParam String image){
        try {
            return ResponseEntity.ok(imageService.getImage(image));
        }catch(IOException e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<?> saveImageToStorage(@RequestPart(value = "image", required = false) MultipartFile image) {

        String pathToStoreImage = "image-management-service/src/main/resources/static/images";
        String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

        try {
            return ResponseEntity.ok(imageService.saveImageToStorage(pathToStoreImage, image, uniqueFileName));
        } catch (IOException e) {
            System.out.println(STR."Error saving image: \{e.getMessage()}");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
