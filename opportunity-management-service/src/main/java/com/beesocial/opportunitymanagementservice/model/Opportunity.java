package com.beesocial.opportunitymanagementservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
public class Opportunity {
    private String documentId;
    private String userId;
    private String text;
    private String image;
    private long timeStamp;

    public Opportunity(String documentId, String  userId, String text, String image, long timestamp) {
        this.documentId = documentId;
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
