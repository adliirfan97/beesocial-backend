package com.beesocial.eventmanagementservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
public class EventApplicant {
    @Id
    @GeneratedValue(generator = "eventApplicant_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "eventApplicant_gen", sequenceName = "eventApplicant_seq", allocationSize = 1)
    private int eventApplicantId;
    private int eventId;
    private int userId;
    private LocalDateTime timestamp =  LocalDateTime.now();

    public EventApplicant(int eventId, int userId) {
        this.eventId = eventId;
        this.userId = userId;
    }
}
