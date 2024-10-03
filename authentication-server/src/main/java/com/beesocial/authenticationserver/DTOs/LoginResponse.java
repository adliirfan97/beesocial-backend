package com.beesocial.authenticationserver.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private int status;
    private String message;
    private String token;

    // Constructor for responses without token (e.g., errors)
    public LoginResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
