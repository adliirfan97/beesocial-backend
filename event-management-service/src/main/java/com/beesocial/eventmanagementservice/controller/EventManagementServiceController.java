package com.beesocial.eventmanagementservice.controller;

import com.beesocial.eventmanagementservice.feign.FirebaseStorageClient;
import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.model.EventApplicant;
import com.beesocial.eventmanagementservice.service.EventService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@RestController
public class EventManagementServiceController {
    private final EurekaDiscoveryClient discoveryClient;
    private final WebClient webClient;
    private final EventService eventService;
    private final FirebaseStorageClient firebaseStorageClient;

    public EventManagementServiceController(EurekaDiscoveryClient discoveryClient, WebClient webClient, EventService eventService, FirebaseStorageClient firebaseStorageClient) {
        this.discoveryClient = discoveryClient;
        this.webClient = webClient;
        this.eventService = eventService;
        this.firebaseStorageClient = firebaseStorageClient;
    }

    @GetMapping("/testEvent")
    public String test() {
        return "Test from Event Management Service";
    }

    // Example of communications from other microservices
    @GetMapping("/testUserFromEvent")
    public String testUserFromEvent() {
        List<ServiceInstance> instances = discoveryClient.getInstances("user-management-service");
        if (instances != null && !instances.isEmpty()) {
            ServiceInstance serviceInstance = instances.getFirst();
            String uri = serviceInstance.getUri().toString() + "/test";
            System.out.println("Service URI: " + uri); // Log for debugging

            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } else {
            return "Service instance not found";
        }
    }

    @PostMapping()
    public ResponseEntity<Object> saveEvent(@RequestBody Event event) {
        Map<String, Object> user = firebaseStorageClient.getData("users", event.getUserId());
        if(user == null || user.get("role") == null ||user.isEmpty()){
            return ResponseEntity.badRequest().body("user not included");
        }
        if(! user.get("role").equals("HR")){
            return ResponseEntity.badRequest().body("user not from HR");
        }
        if(eventService.saveEvent(event).getStatusCode().is4xxClientError()){
                return ResponseEntity.badRequest().body(eventService.saveEvent(event).getBody());
        }
        return ResponseEntity.ok(firebaseStorageClient.saveData("events", (Map<String, Object>) eventService.saveEvent(event).getBody()));
    }
    @PostMapping("/eventApplicant")
    public ResponseEntity<Object> addApplicantToEvent(@RequestBody EventApplicant eventApplicant) {
        Map<String, Object> event = firebaseStorageClient.getData("events" ,eventApplicant.getEventId());
        Map<String, Object> user = firebaseStorageClient.getData("users", eventApplicant.getUserId());
        if(user==null || user.isEmpty()){
            return ResponseEntity.badRequest().body("user does not exist");
        }
        if (event == null || event.isEmpty()){
            return ResponseEntity.badRequest().body("event do not exist");
        }
        if(eventService.addApplicant(eventApplicant).getStatusCode().is4xxClientError()){
            return ResponseEntity.badRequest().body(eventService.addApplicant(eventApplicant).getBody());
        }
        return ResponseEntity.ok(firebaseStorageClient.saveData("eventApplicants", (Map<String, Object>) eventService.addApplicant(eventApplicant).getBody()));
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<Object> editEvent(@PathVariable String documentId, @RequestBody Event event) {
        Map<String, Object> user = firebaseStorageClient.getData("users", event.getUserId());
        if(user == null ||user.get("role")==null|| user.isEmpty()){
            return ResponseEntity.badRequest().body("user not included");
        }
        if(!user.get("role").equals("HR")){
            return ResponseEntity.badRequest().body("user not from HR");
        }
        if(eventService.saveEvent(event).getStatusCode().is4xxClientError()){
            return ResponseEntity.badRequest().body(eventService.saveEvent(event).getBody());
        }
        return ResponseEntity.ok(firebaseStorageClient.editData("events", documentId, (Map<String, Object>) eventService.editEvent(event).getBody()));
    }

    @GetMapping("/getUser")
    public String getUser(@RequestParam String email) {
        List<ServiceInstance> instances = discoveryClient.getInstances("firebase-storage-service");
        if (instances != null && !instances.isEmpty()) {
            ServiceInstance serviceInstance = instances.getFirst();
            String uri = serviceInstance.getUri().toString() + "/api/firebase/user/" + email;
            System.out.println("Service URI: " + uri); // Log for debugging

            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } else {
            return "Service instance not found";
        }
    }

    @GetMapping("/allEvents")
    public ResponseEntity<List<Map<String, Object>>> getAllEvents(){
        return ResponseEntity.ok(firebaseStorageClient.getAllData("events"));
    }
}
