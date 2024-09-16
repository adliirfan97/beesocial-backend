package com.beesocial.eventmanagementservice.controller;

import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.service.EventService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/api/event")
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
    public String saveEvent(@RequestBody Event event) {
        List<ServiceInstance> instances = discoveryClient.getInstances("firebase-storage-service");
        if(instances != null && !instances.isEmpty()){
            ServiceInstance serviceInstance = instances.getFirst();
            String uri = serviceInstance.getUri().toString() + "/api/firebase/events";
            System.out.println("Service URI: "+uri);

            return webClient.post()
                    .uri(uri)
                    .bodyValue(eventService.saveEvent(event))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }else{
            return "Service instance not found";
        }
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
}
