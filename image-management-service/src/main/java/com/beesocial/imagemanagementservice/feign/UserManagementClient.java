//package com.beesocial.contentmanagementservice.feign;
//
//import com.beesocial.contentmanagementservice.dto.UserResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.Optional;
//
//@FeignClient(name = "user-management-service", url = "http://localhost:8081")
//public interface UserManagementClient {
//    @GetMapping("/user/{id}")
//    public Optional<UserResponse> getUserById(@PathVariable int id);
//    @PostMapping("/new")
//    public UserResponse saveNewUser(UserResponse userDTO);
//}
