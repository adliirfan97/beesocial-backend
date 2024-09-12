package com.beesocial.eventmanagementservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Event {
    private int eventId;
    private int userId;
    private String text;
    private String image;
    private LocalDateTime timestamp;
    private boolean isEdited;

    public Event(int userId) {
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
    }
}
