package com.beesocial.eventmanagementservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Event {
    private int eventId;
    private int userId;
    private String text;
    private String image;

    public Event(int eventId, int userId, String text, String image) {
        this.eventId = eventId;
        this.userId = userId;
        this.text = text;
        this.image = image;
    }
}
