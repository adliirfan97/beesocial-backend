package com.beesocial.eventmanagementservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserDTO {
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
    private ROLE role;

    public UserDTO(int userId, String firstName, String lastName, String email, String phoneNumber, String password, String profilePhoto, ROLE role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.role = role;
    }
}
