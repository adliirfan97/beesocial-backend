package com.beesocial.eventmanagementservice.controller;

import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;
    @PostMapping
    public ResponseEntity<?> saveEvent(@RequestBody Event event){
        return eventService.saveEvent(event);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editEventById(@PathVariable int id, @RequestBody Event updatedEvent){
        return eventService.editEventById(id, updatedEvent);
    }
    @PostMapping("/addApplicant")
    public ResponseEntity<?> addApplicantById(@RequestParam int eventId, @RequestParam int userId){
        return eventService.addApplicantById(eventId, userId);
    }
    @GetMapping
    public ResponseEntity<?> getAllEvents(){
        return eventService.getAllEvents();
    }
}
