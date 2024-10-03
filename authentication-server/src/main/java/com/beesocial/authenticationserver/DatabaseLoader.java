package com.beesocial.authenticationserver;

import com.beesocial.authenticationserver.DTOs.Role;
import com.beesocial.authenticationserver.DTOs.RegisterRequest;
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
        RegisterRequest user1 = new RegisterRequest();
        user1.setFirstName("Harry");
        user1.setLastName("Potter");
        user1.setEmail("harry.potter@fdmgroup.com");
        user1.setPassword("password");
        user1.setPhoneNumber("87354721");

        authController.register(user1);

        RegisterRequest user2 = new RegisterRequest();
        user2.setFirstName("Henry");
        user2.setLastName("Cavill");
        user2.setEmail("henrycavill@fdmgroup.com");
        user2.setPassword("password");
        user2.setPhoneNumber("90909090");
        user2.setProfilePhoto("src\\pages\\HomePage\\Events\\images\\henrycavil.jpg");
        user2.setRole(Role.HR);

        authController.register(user2);

        RegisterRequest user3 = new RegisterRequest();
        user3.setFirstName("Emilia");
        user3.setLastName("Clarke");
        user3.setEmail("emiliaclarke@fdmgroup.com");
        user3.setPassword("password");
        user3.setPhoneNumber("91919191");
        user3.setProfilePhoto("src\\pages\\HomePage\\Events\\images\\emiliaclarke.jpg");

        authController.register(user3);
    }
}
