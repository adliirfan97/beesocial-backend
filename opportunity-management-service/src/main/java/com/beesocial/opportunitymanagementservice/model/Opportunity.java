package com.beesocial.opportunitymanagementservice.model;

import lombok.*;

@Data
@NoArgsConstructor
public class Opportunity {
    private String documentId;
    private String userId;
    private String text;
    private String url;
    private long timeStamp;

    public Opportunity(String documentId, String  userId, String text, String url, long timestamp) {
        this.documentId = documentId;
        this.userId = userId;
        this.text = text;
        this.url = url;
        this.timeStamp = timestamp;
    }

    public Opportunity(String  userId, String text, String url, long timestamp) {
        this.userId = userId;
        this.text = text;
        this.url = url;
        this.timeStamp = timestamp;
    }
}
