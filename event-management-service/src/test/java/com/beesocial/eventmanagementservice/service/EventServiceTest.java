package com.beesocial.eventmanagementservice.service;

import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.model.EventApplicant;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventServiceTest {
    @Test
    public void testEvent_checkTime(){
        Event eventReturn = Event.builder()
                .userId(1)
                .build();

        assertEquals(LocalDateTime.now(), eventReturn.getTimestamp());
    }
    @Test
    public void testEvent_checkUserId(){
        EventService eventService = new EventService();
        Event eventReturn = Event.builder()
                .build();

        assertEquals(ResponseEntity.badRequest().body("no host for event").getStatusCode(), eventService.saveEvent(eventReturn).getStatusCode());
    }

    @Test
    public void testSaveEvent_imageAndText(){
        EventService eventService = new EventService();

        Event eventReturn = Event.builder()
                .userId(1)
                .text("event post text")
                .image("image.png")
                .build();

        assertEquals(ResponseEntity.ok().body("").getStatusCode(), eventService.saveEvent(eventReturn).getStatusCode());
    }
    @Test
    public void testSaveEvent_imageOnly(){
        EventService eventService = new EventService();

        Event eventReturn = Event.builder()
                .userId(1)
                .image("image.png")
                .build();

        assertEquals(ResponseEntity.ok().body("").getStatusCode(), eventService.saveEvent(eventReturn).getStatusCode());
    }
    @Test
    public void testSaveEvent_textOnly(){
        EventService eventService = new EventService();

        Event eventReturn = Event.builder()
                .userId(1)
                .text("event post text")
                .build();

        assertEquals(ResponseEntity.ok().body("").getStatusCode(), eventService.saveEvent(eventReturn).getStatusCode());
    }
    @Test
    public void testSaveEvent_nullTextAndNullImage(){
        EventService eventService = new EventService();

        Event eventReturn = Event.builder()
                .userId(1)
                .build();

        assertEquals(ResponseEntity.badRequest().body("no text and no image").getStatusCode(), eventService.saveEvent(eventReturn).getStatusCode());
    }
    @Test
    public void testSaveEvent_emptyTextAndEmptyImage(){
        EventService eventService = new EventService();

        Event eventReturn = Event.builder()
                .text("")
                .image("")
                .build();

        assertEquals(ResponseEntity.badRequest().body("no text and no image").getStatusCode(), eventService.saveEvent(eventReturn).getStatusCode());
    }
    @Test
    public void testSaveEvent_textMoreThan2000Characters(){
        EventService eventService = new EventService();
        String text = "Lorem ipsum odor amet, consectetuer adipiscing elit. Maximus vestibulum donec faucibus aptent sed phasellus. Dictum nisl ad volutpat habitasse massa eleifend malesuada. Sit conubia massa imperdiet eget cursus felis diam vivamus. Facilisi pulvinar egestas consequat enim consectetur cras nulla. Eleifend quis nam quisque ultricies morbi sociosqu natoque vel laoreet. Commodo netus imperdiet consectetur porttitor nascetur tortor aliquam. Taciti consequat sodales ad quam gravida sem." +
                "Lorem ipsum odor amet, consectetuer adipiscing elit. Maximus vestibulum donec faucibus aptent sed phasellus. Dictum nisl ad volutpat habitasse massa eleifend malesuada. Sit conubia massa imperdiet eget cursus felis diam vivamus. Facilisi pulvinar egestas consequat enim consectetur cras nulla. Eleifend quis nam quisque ultricies morbi sociosqu natoque vel laoreet. Commodo netus imperdiet consectetur porttitor nascetur tortor aliquam. Taciti consequat sodales ad quam gravida sem." +
                "Lorem ipsum odor amet, consectetuer adipiscing elit. Maximus vestibulum donec faucibus aptent sed phasellus. Dictum nisl ad volutpat habitasse massa eleifend malesuada. Sit conubia massa imperdiet eget cursus felis diam vivamus. Facilisi pulvinar egestas consequat enim consectetur cras nulla. Eleifend quis nam quisque ultricies morbi sociosqu natoque vel laoreet. Commodo netus imperdiet consectetur porttitor nascetur tortor aliquam. Taciti consequat sodales ad quam gravida sem." +
                "Lorem ipsum odor amet, consectetuer adipiscing elit. Maximus vestibulum donec faucibus aptent sed phasellus. Dictum nisl ad volutpat habitasse massa eleifend malesuada. Sit conubia massa imperdiet eget cursus felis diam vivamus. Facilisi pulvinar egestas consequat enim consectetur cras nulla. Eleifend quis nam quisque ultricies morbi sociosqu natoque vel laoreet. Commodo netus imperdiet consectetur porttitor nascetur tortor aliquam. Taciti consequat sodales ad quam gravida sem." +
                "Lorem ipsum odor amet, consectetuer adipiscing elit. Maximus vestibulum donec faucibus aptent sed phasellus. Dictum nisl ad volutpat habitasse massa eleifend malesuada. Sit conubia massa imperdiet eget cursus felis diam vivamus. Facilisi pulvinar egestas consequat enim consectetur cras nulla. Eleifend quis nam quisque ultricies morbi sociosqu natoque vel laoreet. Commodo netus imperdiet consectetur porttitor nascetur tortor aliquam. Taciti consequat sodales ad quam gravida sem.";

        Event eventReturn = Event.builder()
                .text(text)
                .build();

        assertEquals(ResponseEntity.badRequest().body("no text and no image").getStatusCode(), eventService.saveEvent(eventReturn).getStatusCode());
    }
    @Test
    public void testAddApplicantById(){
        EventService eventService = new EventService();

        EventApplicant eventApplicant = new EventApplicant(1, 1);

        assertEquals(eventApplicant, eventService.addApplicantById(1, 1).getBody());
    }
}
