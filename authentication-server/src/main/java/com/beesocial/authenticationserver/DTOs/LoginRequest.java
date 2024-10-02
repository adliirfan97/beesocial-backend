package com.beesocial.authenticationserver.DTOs;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
