package com.beesocial.eventmanagementservice.feign;

import com.beesocial.eventmanagementservice.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "user-management-service", url = "http://localhost:8081")
public interface UserManagementClient {
    @GetMapping("/{id}")
    public Optional<UserDTO> getUserById(int id);
    @PostMapping("/new")
    public UserDTO saveNewUser(UserDTO userDTO);
}
