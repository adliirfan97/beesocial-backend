package com.beesocial.eventmanagementservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EventApplicant {
    private String eventId;
    private String userId;
    private LocalDateTime timestamp =  LocalDateTime.now();

    public EventApplicant(String eventId, String userId) {
        this.eventId = eventId;
        this.userId = userId;
    }
}
