package com.beesocial.authenticationserver.DTOs;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
