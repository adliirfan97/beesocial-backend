package com.beesocial.eventmanagementservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EventApplicant {
    private int eventId;
    private int userId;
    private LocalDateTime timestamp;

    public EventApplicant(int eventId, int userId) {
        this.eventId = eventId;
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
    }
}
