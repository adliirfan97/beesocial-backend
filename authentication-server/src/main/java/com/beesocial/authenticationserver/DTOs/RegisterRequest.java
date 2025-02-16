package com.beesocial.authenticationserver.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotEmpty(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    private String lastName;
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    private String phoneNumber;
}
