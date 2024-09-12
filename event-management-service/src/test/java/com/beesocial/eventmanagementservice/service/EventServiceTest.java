package com.beesocial.eventmanagementservice.service;

import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.model.EventApplicant;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventServiceTest {
    @Test
    public void testEvent_checkTime(){
        Event eventReturn = new Event(1);

        assertEquals(LocalDateTime.now(), eventReturn.getTimestamp());
    }
    @Test
    public void testSaveEvent_imageAndText(){
        EventService eventService = new EventService();

        Event eventExpected = new Event(1);
        eventExpected.setText("event post text");
        eventExpected.setImage("image.png");

        Event eventReturn = new Event(1);
        eventReturn.setText("event post text");
        eventReturn.setImage("image.png");

        assertEquals(eventExpected, eventService.saveEvent(eventReturn).getBody());
    }
    @Test
    public void testSaveEvent_imageOnly(){
        EventService eventService = new EventService();

        Event eventReturn = new Event(1);
        eventReturn.setImage("image.png");

        Event eventExpected = new Event(1);
        eventExpected.setImage("image.png");

        assertEquals(eventExpected, eventService.saveEvent(eventReturn).getBody());
    }
    @Test
    public void testSaveEvent_textOnly(){
        EventService eventService = new EventService();

        Event eventReturn = new Event(1);
        eventReturn.setText("event post text");

        Event eventExpected = new Event(1);
        eventExpected.setText("event post text");


        assertEquals(eventExpected, eventService.saveEvent(eventReturn).getBody());
    }
    @Test
    public void testSaveEvent_nullTextAndNullImage(){
        EventService eventService = new EventService();

        Event eventReturn = new Event(1);

        assertEquals(ResponseEntity.badRequest().body("no text and no image").getStatusCode(), eventService.saveEvent(eventReturn).getStatusCode());
    }
    @Test
    public void testSaveEvent_emptyTextAndEmptyImage(){
        EventService eventService = new EventService();

        Event eventReturn = new Event(1);
        eventReturn.setImage("");
        eventReturn.setText("");

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

        Event eventReturn = new Event(1);
        eventReturn.setText(text);

        assertEquals(ResponseEntity.badRequest().body("no text and no image").getStatusCode(), eventService.saveEvent(eventReturn).getStatusCode());
    }
    @Test
    public void testAddApplicantById(){
        EventService eventService = new EventService();

        EventApplicant eventApplicant = new EventApplicant(1, 1);

        assertEquals(eventApplicant, eventService.addApplicantById(1, 1).getBody());
    }
}
