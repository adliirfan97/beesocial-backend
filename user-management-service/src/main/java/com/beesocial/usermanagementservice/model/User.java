package com.beesocial.usermanagementservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_generator")
    @SequenceGenerator(name = "User_generator", sequenceName = "User_seq", allocationSize = 1)
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String profilePhoto;
    private String username;
    private ROLE role;

    public User(String firstName, String lastName, String email, String phoneNumber, String password, String profilePhoto, String username, ROLE role) {
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
