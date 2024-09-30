package com.beesocial.usermanagementservice.controller;

import com.beesocial.usermanagementservice.model.User;
import com.beesocial.usermanagementservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserManagementServiceController {

    private final UserService userService;

    public UserManagementServiceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }
}
