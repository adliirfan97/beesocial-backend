package com.beesocial.eventmanagementservice.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
public class EventManagementServiceController {
    private final EurekaDiscoveryClient discoveryClient;
    private final WebClient webClient;

    public EventManagementServiceController(EurekaDiscoveryClient discoveryClient, WebClient webClient) {
        this.discoveryClient = discoveryClient;
        this.webClient = webClient;
    }

    @GetMapping("/testEvent")
    public String test() {
        return "Test from Event Management Service";
    }

    @GetMapping("/testUserFromEvent")
    public String testUserFromEvent() {
        List<ServiceInstance> instances = discoveryClient.getInstances("user-management-service");
        if (instances != null && instances.isEmpty()) {
            ServiceInstance serviceInstance = instances.getFirst();
            String uri = serviceInstance.getUri().toString() + "/test";
            System.out.println("Service URI: " + uri); // Log for debugging

            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
        return "Service instance not found";
    }
}
