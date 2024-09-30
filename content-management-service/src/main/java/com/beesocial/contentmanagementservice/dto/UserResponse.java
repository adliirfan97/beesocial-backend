package com.beesocial.contentmanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private int userId;
    private String firstName;
    private String lastName;
    private String profilePhoto;
}
