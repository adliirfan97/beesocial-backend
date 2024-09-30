package com.beesocial.eventmanagementservice.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_gen")
    @SequenceGenerator(name = "event_gen", sequenceName = "event_seq", allocationSize = 1)
    private int eventId;
    private int userId;
    private String text;
    private String image;
    private LocalDateTime timestamp = LocalDateTime.now();
    private boolean isEdited = false;

    @Builder
    public Event(int userId, String text, String image) {
        this.userId = userId;
        this.text = text;
        this.image = image;
    }
}
