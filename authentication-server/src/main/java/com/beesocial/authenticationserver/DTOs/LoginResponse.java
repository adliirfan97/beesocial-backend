package com.beesocial.authenticationserver.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class LoginResponse {
    private int status;
    private String message;
    private String token;
    private Map<String, String> fieldErrors;
}
