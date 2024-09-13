package com.beesocial.eventmanagementservice.service;

import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.model.EventApplicant;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class EventService {
    public ResponseEntity<Object> saveEvent(Event event){
        String text = event.getText();
        String image = event.getImage();
        if(event.getUserId() == 0){
            return ResponseEntity.badRequest().body("no host for event");
        }
        if((text == null || text.isEmpty()) && (image == null || image.isEmpty())){
            return ResponseEntity.badRequest().body("no text and no image");
        }
        if(!(text==null||text.isEmpty())){
            if(text.length()>=2000){
                return ResponseEntity.badRequest().body("text too long");
            }
            event.setText(text);
        }

        // ADD: save event to database
        return ResponseEntity.ok(event);
    }
    public ResponseEntity<Object> addApplicantById(int eventId, int userId){
        // ADD: validation checks the existence of eventId and userId
        // ADD: validation checks if the user has already applied to the event

        EventApplicant eventApplicant = new EventApplicant(eventId, userId);

        return ResponseEntity.ok(eventApplicant);
    }
    public ResponseEntity<Object> editEventById(int eventId, Event updatedEvent){
        // ADD: find eventById

        // ADD: set the new updates

        // ADD: save updated event
        return ResponseEntity.ok("updatedEvent");
    }
}
