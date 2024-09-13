package com.beesocial.firebasestorageservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String profilePhoto;

    public User(String firstName, String email) {
        this.firstName = firstName;
        this.email = email;
    }

}
