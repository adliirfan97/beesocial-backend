package com.beesocial.opportunitymanagementservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Opportunity_generator")
    @SequenceGenerator(name = "Opportunity_generator", sequenceName = "Opportunity_seq", allocationSize = 1)
    private int opportunityId;
    private int userId;
    private String text;
    private String url;
    private LocalDateTime timeStamp;
    @ElementCollection
    private List<String> applicantIds;

    public Opportunity(int userId, String text, String url, LocalDateTime timestamp) {
        this.userId = userId;
        this.text = text;
        this.url = url;
        this.timeStamp = timestamp;
    }
}
