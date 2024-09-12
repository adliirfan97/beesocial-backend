package com.beesocial.eventmanagementservice.service;

import com.beesocial.eventmanagementservice.model.Event;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventServiceTest {
    @Test
    public void testSaveEvent_imageAndText(){
        EventService eventService = new EventService();

        Event eventReturn = new Event(1, LocalDateTime.now());
        eventReturn.setText("event post text");
        eventReturn.setImage("image.png");

        assertEquals(eventReturn, eventService.saveEvent(1, "event post text", "image.png").getBody());
    }
    @Test
    public void testSaveEvent_imageOnly(){
        EventService eventService = new EventService();

        Event eventReturn = new Event(1, LocalDateTime.now());
        eventReturn.setImage("image.png");

        assertEquals(eventReturn, eventService.saveEvent(1, "", "image.png").getBody());
    }
    @Test
    public void testSaveEvent_textOnly(){
        EventService eventService = new EventService();

        Event eventReturn = new Event(1, LocalDateTime.now());
        eventReturn.setText("event post text");

        assertEquals(eventReturn, eventService.saveEvent(1, "event post text", "").getBody());
    }
    @Test
    public void testSaveEvent_nullTextAndNullImage(){
        EventService eventService = new EventService();

        assertEquals(ResponseEntity.badRequest().body("no text and no image").getStatusCode(), eventService.saveEvent(1, null, null).getStatusCode());
    }
    @Test
    public void testSaveEvent_emptyTextAndEmptyImage(){
        EventService eventService = new EventService();

        assertEquals(ResponseEntity.badRequest().body("no text and no image").getStatusCode(), eventService.saveEvent(1, "", "").getStatusCode());
    }
    @Test
    public void testSaveEvent_textMoreThan2000Characters(){
        EventService eventService = new EventService();
        String text = "Lorem ipsum odor amet, consectetuer adipiscing elit. Maximus vestibulum donec faucibus aptent sed phasellus. Dictum nisl ad volutpat habitasse massa eleifend malesuada. Sit conubia massa imperdiet eget cursus felis diam vivamus. Facilisi pulvinar egestas consequat enim consectetur cras nulla. Eleifend quis nam quisque ultricies morbi sociosqu natoque vel laoreet. Commodo netus imperdiet consectetur porttitor nascetur tortor aliquam. Taciti consequat sodales ad quam gravida sem." +
                "Lorem ipsum odor amet, consectetuer adipiscing elit. Maximus vestibulum donec faucibus aptent sed phasellus. Dictum nisl ad volutpat habitasse massa eleifend malesuada. Sit conubia massa imperdiet eget cursus felis diam vivamus. Facilisi pulvinar egestas consequat enim consectetur cras nulla. Eleifend quis nam quisque ultricies morbi sociosqu natoque vel laoreet. Commodo netus imperdiet consectetur porttitor nascetur tortor aliquam. Taciti consequat sodales ad quam gravida sem." +
                "Lorem ipsum odor amet, consectetuer adipiscing elit. Maximus vestibulum donec faucibus aptent sed phasellus. Dictum nisl ad volutpat habitasse massa eleifend malesuada. Sit conubia massa imperdiet eget cursus felis diam vivamus. Facilisi pulvinar egestas consequat enim consectetur cras nulla. Eleifend quis nam quisque ultricies morbi sociosqu natoque vel laoreet. Commodo netus imperdiet consectetur porttitor nascetur tortor aliquam. Taciti consequat sodales ad quam gravida sem." +
                "Lorem ipsum odor amet, consectetuer adipiscing elit. Maximus vestibulum donec faucibus aptent sed phasellus. Dictum nisl ad volutpat habitasse massa eleifend malesuada. Sit conubia massa imperdiet eget cursus felis diam vivamus. Facilisi pulvinar egestas consequat enim consectetur cras nulla. Eleifend quis nam quisque ultricies morbi sociosqu natoque vel laoreet. Commodo netus imperdiet consectetur porttitor nascetur tortor aliquam. Taciti consequat sodales ad quam gravida sem." +
                "Lorem ipsum odor amet, consectetuer adipiscing elit. Maximus vestibulum donec faucibus aptent sed phasellus. Dictum nisl ad volutpat habitasse massa eleifend malesuada. Sit conubia massa imperdiet eget cursus felis diam vivamus. Facilisi pulvinar egestas consequat enim consectetur cras nulla. Eleifend quis nam quisque ultricies morbi sociosqu natoque vel laoreet. Commodo netus imperdiet consectetur porttitor nascetur tortor aliquam. Taciti consequat sodales ad quam gravida sem.";

        assertEquals(ResponseEntity.badRequest().body("no text and no image").getStatusCode(), eventService.saveEvent(1, text, "").getStatusCode());
    }

}
