package com.beesocial.usermanagementservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserManagementServiceController {

    @GetMapping("/test")
    public String test() {
        return "Testing";
    }
}
