package com.beesocial.eventmanagementservice.controller;

import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.service.EventService;
import com.beesocial.eventmanagementservice.service.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private ImageService imageService;
    @PostMapping("/events")
    public ResponseEntity<?> saveEvent(
            @RequestPart("event") String eventJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        ObjectMapper objectMapper = new ObjectMapper();
        Event event;

        try {
            event = objectMapper.readValue(eventJson, Event.class);
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing event JSON: " + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid event data");
        }

        if (image != null && !image.isEmpty()) {
            String path = "event-management-service/src/main/resources/static/images";
            try {
                String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                event.setImage(path + "/" + uniqueFileName);
                imageService.saveImageToStorage(path, image, uniqueFileName);
            } catch (IOException e) {
                System.out.println("Error saving image: " + e.getMessage());
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return eventService.saveEvent(event);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editEventById(@PathVariable int id, @RequestBody Event updatedEvent){
        return eventService.editEventById(id, updatedEvent);
    }
    @PostMapping("/addApplicant")
    public ResponseEntity<?> addApplicantById(@RequestParam int eventId, @RequestParam int userId){
        return eventService.addApplicantById(eventId, userId);
    }
    @GetMapping
    public ResponseEntity<?> getAllEvents(){
        return eventService.getAllEvents();
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
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        return eventService.getuserById(id);
    }
}
