package com.beesocial.opportunitymanagementservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
public class Opportunity {
    private int opportunityId;
    private String userId;
    private String text;
    private String image;
    private long timeStamp;

    public Opportunity(int opportunityId, String  userId, String text, String image, long timestamp) {
        this.opportunityId = opportunityId;
        this.userId = userId;
        this.text = text;
        this.image = image;
        this.timeStamp = timestamp;
    }

    public Opportunity( String  userId, String text, String image, long timestamp) {
        this.userId = userId;
        this.text = text;
        this.image = image;
        this.timeStamp = timestamp;
    }
}
