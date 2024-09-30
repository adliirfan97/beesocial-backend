package com.beesocial.authenticationserver.DTOs;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String profilePhoto;
}
