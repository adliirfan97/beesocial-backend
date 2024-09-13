package com.beesocial.eventmanagementservice.model;

import lombok.Builder;
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

    @Builder
    public Event(int eventId, int userId, String text, String image, LocalDateTime timestamp, boolean isEdited) {
        this.eventId = eventId;
        this.userId = userId;
        this.text = text;
        this.image = image;
        this.timestamp = LocalDateTime.now();
        this.isEdited = false;
    }
}
