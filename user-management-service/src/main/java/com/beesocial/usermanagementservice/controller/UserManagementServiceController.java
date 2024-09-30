package com.beesocial.usermanagementservice.controller;

import com.beesocial.usermanagementservice.model.User;
import com.beesocial.usermanagementservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserManagementServiceController {
    private final UserService userService;

    @Autowired
    public UserManagementServiceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
        return "Testing";
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/new")
    public User saveNewUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteExistingUserById(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    public User updateExistingUserById(@PathVariable int id){
        return userService.updateUser(id);
    }

}
