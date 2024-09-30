package com.beesocial.eventmanagementservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String profilePhoto;
    private String username;
    private ROLE role;

    public UserDTO(int userId, String firstName, String lastName, String email, String phoneNumber, String password, String profilePhoto, String username, ROLE role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.username = username;
        this.role = role;
    }
}
