package com.beesocial.eventmanagementservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Event {
    private String userId;
    private String text;
    private String image;
    private LocalDateTime timestamp = LocalDateTime.now();
    private boolean isEdited = false;

    @Builder
    public Event(String userId, String text, String image) {
        this.userId = userId;
        this.text = text;
        this.image = image;
    }
}
