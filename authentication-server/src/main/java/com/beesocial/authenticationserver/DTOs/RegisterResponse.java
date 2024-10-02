package com.beesocial.authenticationserver.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private int status;
    private String message;
}
