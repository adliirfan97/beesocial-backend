package com.beesocial.opportumitymanagementservice.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String image;
    private long timestamp;

    public Opportunity(int opportunityId, int userId, String text, String image, long timestamp) {
        this.opportunityId = opportunityId;
        this.userId = userId;
        this.text = text;
        this.image = image;
        this.timestamp = timestamp;
    }
}
