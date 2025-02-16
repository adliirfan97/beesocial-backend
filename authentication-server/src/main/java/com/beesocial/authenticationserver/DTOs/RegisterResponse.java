package com.beesocial.authenticationserver.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class RegisterResponse {
    private int status;
    private String message;
    private Map<String, String> fieldErrors;
}
