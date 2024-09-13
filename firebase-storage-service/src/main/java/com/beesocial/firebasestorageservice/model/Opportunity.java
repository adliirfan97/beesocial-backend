package com.beesocial.firebasestorageservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Opportunity {
    private int userId;
    private String text;
    private String image;
    private long timeStamp;

}
