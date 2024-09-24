package com.beesocial.eventmanagementservice.feign;

import com.beesocial.eventmanagementservice.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user-management-service")
public interface UserManagementClient {
    @GetMapping("/{id}")
    public Optional<UserDTO> getUserById(@PathVariable int id);
}
