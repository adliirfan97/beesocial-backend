package com.beesocial.eventmanagementservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private int userId;
    private ROLE role;

    public UserDTO(int userId, ROLE role) {
        this.userId = userId;
        this.role = role;
    }
}
