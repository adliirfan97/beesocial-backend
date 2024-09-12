package com.beesocial.eventmanagementservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventApplicant {
    private int eventId;
    private int userId;

    public EventApplicant(int eventId, int userId) {
        this.eventId = eventId;
        this.userId = userId;
    }
}
