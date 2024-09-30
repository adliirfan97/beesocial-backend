package com.beesocial.authenticationserver;

import com.beesocial.authenticationserver.DTOs.User;
import com.beesocial.authenticationserver.DTOs.UserRegistrationRequest;
import com.beesocial.authenticationserver.controller.AuthController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class DatabaseLoader implements ApplicationRunner {
    private final AuthController authController;

    public DatabaseLoader(AuthController authController) {
        this.authController = authController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserRegistrationRequest user = new UserRegistrationRequest();
        user.setFirstName("Harry");
        user.setLastName("Potter");
        user.setEmail("harry.potter@fdmgroup.com");
        user.setPassword("password");
        user.setPhoneNumber("87354721");

        authController.register(user);
    }
}
