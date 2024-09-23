package com.beesocial.eventmanagementservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user-management-service")
public interface UserManagementClient {
    @GetMapping("/user/{id}")
    public Optional<?> getUserById(@PathVariable int id);
}
