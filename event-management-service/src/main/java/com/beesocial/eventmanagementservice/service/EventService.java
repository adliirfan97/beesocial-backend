package com.beesocial.eventmanagementservice.service;

import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.model.EventApplicant;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class EventService {
    public ResponseEntity<Object> saveEvent(int userId, String text, String image){
        Event event = new Event(userId, LocalDateTime.now());
        if((text == null || text.isEmpty()) && (image == null || image.isEmpty())){
            return ResponseEntity.badRequest().body("no text and no image");
        }
        if(!text.isEmpty() && !image.isEmpty()){
            event.setText(text);
            event.setImage(image);
        }
        if(text.isEmpty() && !image.isEmpty()){
            event.setImage(image);
        }
        if(!text.isEmpty() && image.isEmpty()){
            if(text.length()>=2000){
                return ResponseEntity.badRequest().body("text too long");
            }
            event.setText(text);
        }
        return ResponseEntity.ok(event);
    }
    public ResponseEntity<Object> addApplicantById(int eventId, int userId){
        EventApplicant eventApplicant = new EventApplicant(eventId, userId);
        return ResponseEntity.ok(eventApplicant);
    }
}
