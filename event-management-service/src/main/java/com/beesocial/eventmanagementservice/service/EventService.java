package com.beesocial.eventmanagementservice.service;

import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.model.EventApplicant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class EventService {
        public ResponseEntity<Object> saveEvent(Event event){
        String text = event.getText();
        String image = event.getImage();
        if(event.getUserId() == null || event.getUserId().isEmpty()){
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

        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("userId", event.getUserId());
        eventMap.put("text", event.getText());
        eventMap.put("image", event.getImage());
        eventMap.put("timestamp", event.getTimestamp());
        eventMap.put("isEdited", event.isEdited());

        // ADD: save event to database
        return ResponseEntity.ok(eventMap);
    }
    public ResponseEntity<Object> addApplicant(EventApplicant eventApplicant){
        String eventId = eventApplicant.getEventId();
        String userId = eventApplicant.getUserId();
        if(eventId==null || userId==null || eventId.isEmpty()||userId.isEmpty()){
            return ResponseEntity.badRequest().body("no userId or eventId");
        }
        Map<String, Object> eventApplicantMap = new HashMap<>();
        eventApplicantMap.put("eventId", eventId);
        eventApplicantMap.put("userId", userId);
        eventApplicantMap.put("timestamp", eventApplicant.getTimestamp());

        return ResponseEntity.ok(eventApplicantMap);
    }
    public ResponseEntity<Object> editEvent(Event event){
        String text = event.getText();
        String image = event.getImage();
        if(event.getUserId() == null || event.getUserId().isEmpty()){
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

        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("userId", event.getUserId());
        eventMap.put("text", event.getText());
        eventMap.put("image", event.getImage());
        eventMap.put("timestamp", event.getTimestamp());
        eventMap.put("isEdited", true);

        // ADD: save event to database
        return ResponseEntity.ok(eventMap);
    }
}
