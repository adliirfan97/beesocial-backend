package com.beesocial.eventmanagementservice.controller;

import com.beesocial.eventmanagementservice.service.EventService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@RestController
public class EventManagementServiceController {
    private final EurekaDiscoveryClient discoveryClient;
    private final WebClient webClient;
    private final EventService eventService;

    public EventManagementServiceController(EurekaDiscoveryClient discoveryClient, WebClient webClient, EventService eventService) {
        this.discoveryClient = discoveryClient;
        this.webClient = webClient;
        this.eventService = eventService;
    }

    @GetMapping("/testEvent")
    public String test() {
        return "Test from Event Management Service";
    }

    // Example of communications from other microservices
    @GetMapping("/testUserFromEvent")
    public String testUserFromEvent() {
        List<ServiceInstance> instances = discoveryClient.getInstances("user-management-service");
        System.out.println(instances);
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

    @PostMapping("/event")
    public ResponseEntity<Object> saveEvent(@RequestParam int userId, @RequestParam String text, @RequestParam String image){
        return eventService.saveEvent(userId, text, image);
    }
}
