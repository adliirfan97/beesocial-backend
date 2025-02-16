package com.beesocial.authenticationserver;

import com.beesocial.authenticationserver.DTOs.Role;
import com.beesocial.authenticationserver.DTOs.User;
import com.beesocial.authenticationserver.controller.AuthController;
import com.beesocial.authenticationserver.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DatabaseLoader implements ApplicationRunner {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DatabaseLoader(AuthController authController, UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createUser(
                "Harry",
                "Potter",
                "harry.potter@fdmgroup.com",
                "password",
                "99999999",
                null,
                Role.EMPLOYEE
                );

        createUser(
                "Henry",
                "Cavill",
                "henrycavill@fdmgroup.com",
                "password",
                "90909090",
                "src\\pages\\HomePage\\Events\\images\\henrycavil.jpg",
                Role.HR
        );

        createUser(
                "Emilia",
                "Clarke",
                "emiliaclarke@fdmgroup.com",
                "password",
                "91919191",
                "src\\pages\\HomePage\\Events\\images\\emiliaclarke.jpg",
                Role.EMPLOYEE
        );
    }

    private void createUser(String firstName, String lastName, String email, String password, String phoneNumber, String profilePhoto, Role role) {
        if (!userService.userExist(email)) {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setPhoneNumber(phoneNumber);
            user.setProfilePhoto(profilePhoto != null ? profilePhoto : "");
            user.setRole(role);

            userService.saveUser(user);
            System.out.println("User " + firstName + " " + lastName + " created");
        } else {
            System.out.println("User " + firstName + " " + lastName + " already exists");
        }
    }
}
