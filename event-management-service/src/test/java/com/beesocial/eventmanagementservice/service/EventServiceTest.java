package com.beesocial.eventmanagementservice.service;

import com.beesocial.eventmanagementservice.feign.UserManagementClient;
import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.model.EventApplicant;
import com.beesocial.eventmanagementservice.model.ROLE;
import com.beesocial.eventmanagementservice.model.UserDTO;
import com.beesocial.eventmanagementservice.repository.EventApplicantRepository;
import com.beesocial.eventmanagementservice.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventApplicantRepository eventApplicantRepository;
    @Mock
    private UserManagementClient userManagementClient;
    @InjectMocks
    private EventService eventService;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void test_removeSpaceFromText(){
        String returnString = eventService.removeSpaceFromText("Hello World! :)");

        assertEquals("HelloWorld!:)", returnString);
    }
    @Test
    public void test_isImageCorrectFormat_true(){
        String imagePath = "blah/blah/blah/image.png";

        assertTrue(eventService.isImageCorrectFormat(imagePath));
    }
    @Test
    public void test_isImageCorrectFormat_false(){
        String imagePath = "blah/blah/blah/image.gif";

        assertFalse(eventService.isImageCorrectFormat(imagePath));
    }
    @Test
    public void test_saveEvent_passed(){
        Event event = new Event(1, "text", "imageURL.png");
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.HR);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));

        ResponseEntity<?> response = eventService.saveEvent(event);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(200);
        assertEquals(expectedCode, actualCode);

        if(response.getBody() instanceof Event actualEvent){
            assertNotNull(actualEvent);
            assertFalse(actualEvent.isEdited());
        }
    }
    @Test
    public void test_saveEvent_failedNoHost(){
        Event event = new Event();
        event.setText("text");
        event.setImage("imageURL");
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        HttpStatusCode actualCode = eventService.saveEvent(event).getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);

        assertEquals(expectedCode, actualCode);

        if( eventService.saveEvent(event).getBody() instanceof String actualError){
            assertEquals("no host", actualError);
        }
    }
    @Test
    public void test_saveEvent_failedNoTextNoImageEmpty(){
        Event event = new Event(1, "", "");
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.HR);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));


        HttpStatusCode actualCode = eventService.saveEvent(event).getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);

        assertEquals(expectedCode, actualCode);

        if(eventService.saveEvent(event).getBody() instanceof String actualError){
            assertEquals("no image and no text", actualError);
        }
    }
    @Test
    public void test_saveEvent_failedNoTextNoImageNull(){
        Event event = new Event(1, null, null);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.HR);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));


        HttpStatusCode actualCode = eventService.saveEvent(event).getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);

        assertEquals(expectedCode, actualCode);

        if(eventService.saveEvent(event).getBody() instanceof String actualError){
            assertEquals("no image and no text", actualError);
        }
    }
    @Test
    public void test_saveEvent_failedTextMoreTan2000(){
        Event event = new Event(1,null, null);
        String text = "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";
        event.setText(text);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.HR);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));


        HttpStatusCode actualCode = eventService.saveEvent(event).getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);

        assertEquals(expectedCode, actualCode);

        if(eventService.saveEvent(event).getBody() instanceof String actualError){
            assertEquals("text more than 2000 characters", actualError);
        }
    }
    @Test
    public void test_saveEvent_ImageNotSupported(){
        Event event = new Event(1,null, "blah/blah/blah/image.gif");

        when(eventRepository.save(any(Event.class))).thenReturn(event);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.HR);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));

        HttpStatusCode actualCode = eventService.saveEvent(event).getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);

        assertEquals(expectedCode, actualCode);

        if(eventService.saveEvent(event).getBody() instanceof String actualError){
            assertEquals("image format not supported", actualError);
        }
    }
    @Test
    public void test_saveEvent_failedNotHR(){
        Event event = new Event(1, "text", "image.png");
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.empty());
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.EMPLOYEE);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));

        ResponseEntity<?> response = eventService.saveEvent(event);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);
        assertEquals(expectedCode, actualCode);

        if(response.getBody() instanceof String actualError){
            assertEquals("user is not HR", actualError);
        }
    }
    @Test
    public void test_saveEvent_failedUserNotFound(){
        Event event = new Event(1, "Text", "image.png");
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.empty());

        ResponseEntity<?> response = eventService.saveEvent(event);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(404);
        assertEquals(expectedCode, actualCode);
    }
    @Test
    public void test_editEventById_passed(){
        Event event = new Event(1, "text", "image.png");
        Event updatedEvent = new Event(1, "new text", "image.png");
        updatedEvent.setEdited(true);

        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.HR);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));

        ResponseEntity<?> response = eventService.editEventById(1, updatedEvent);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(200);
        assertEquals(expectedCode, actualCode);

        if(response.getBody() instanceof Event actualEvent){
            assertNotNull(actualEvent);
            assertTrue(actualEvent.isEdited(), "The event's isEdited flag should be true after editing");
        }
    }
    @Test
    public void test_editEventById_failedEventNotFound(){
        Event updatedEvent = new Event(1, "New text", "image.png ");
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        ResponseEntity<?> response = eventService.editEventById(1, updatedEvent);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(404);

        assertEquals(expectedCode,actualCode);
    }
    @Test
    public void test_editEventById_failedEventNoHost(){
        Event event = new Event(1, "text", "image.png");
        Event updatedEvent = new Event();
        updatedEvent.setText("New text");
        updatedEvent.setImage("image.png");
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.of(event));

        ResponseEntity<?> response = eventService.editEventById(1, updatedEvent);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);
        assertEquals(expectedCode,actualCode);

        if(response.getBody() instanceof String actualError){
            assertEquals("no host", actualError);
        }
    }
    @Test
    public void test_editEventById_failedEventNoImageNoTextEmpty(){
        Event event = new Event(1, "text", "image.png");
        Event updatedEvent = new Event(1,  " ", " ");
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.of(event));
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.HR);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));


        ResponseEntity<?> response = eventService.editEventById(1, updatedEvent);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);
        assertEquals(expectedCode,actualCode);

        if(response.getBody() instanceof String actualError) {
            assertEquals("no image and no text", actualError);
        }
    }
    @Test
    public void test_editEventById_failedEventNoImageNoTextNull(){
        Event event = new Event(1, "text", "image.png");
        Event updatedEvent = new Event(1,  null, null);
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.of(event));
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.HR);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));


        ResponseEntity<?> response = eventService.editEventById(1, updatedEvent);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);
        assertEquals(expectedCode,actualCode);

        if(response.getBody() instanceof String actualError) {
            assertEquals("no image and no text", actualError);
        }
    }
    @Test
    public void test_editEventById_failedText2000(){
        Event event = new Event(1, "text", "image.png");
        String text = "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";
        Event updatedEvent = new Event(1,  text, null);
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.of(event));
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.HR);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));

        ResponseEntity<?> response = eventService.editEventById(1, updatedEvent);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);
        assertEquals(expectedCode,actualCode);

        if(response.getBody() instanceof String actualError) {
            assertEquals("text more than 2000 characters", actualError);
        }
    }
    @Test
    public void test_editEventById_failedImageNotSupported(){
        Event event = new Event(1, "text", "image.png");
        Event updatedEvent = new Event(1,  null, "image.gif");
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.of(event));
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.HR);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));

        ResponseEntity<?> response = eventService.editEventById(1, updatedEvent);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);
        assertEquals(expectedCode,actualCode);

        if(response.getBody() instanceof String actualError) {
            assertEquals("image format not supported", actualError);
        }
    }
    @Test
    public void test_editEventById_failedUserNotFound(){
        Event event = new Event(1, "text", "image.png");
        Event updatedEvent = new Event(1, "New text", "image.png");
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.of(event));
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.empty());

        ResponseEntity<?> response = eventService.editEventById(1, updatedEvent);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expected = HttpStatusCode.valueOf(404);
        assertEquals(expected, actualCode);
    }
    @Test
    public void test_editEventById_failedNotHR(){
        Event event = new Event(1, "text", "image.jpg");
        Event updatedEvent = new Event(1, "new text", "image.jpg");
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.of(event));
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setRole(ROLE.EMPLOYEE);
        when(userManagementClient.getUserById(any(Integer.class))).thenReturn(Optional.of(userDTO));

        ResponseEntity<?> response = eventService.editEventById(1, updatedEvent);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);
        assertEquals(expectedCode, actualCode);

        if(response.getBody() instanceof String actualError) {
            assertEquals("user is not HR", actualError);
        }
    }
    @Test
    public void test_addApplicantById_passed(){
        Event event = new Event(1, "Text", "Image.png");
        EventApplicant eventApplicant = new EventApplicant(1, 1);
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.of(event));
        when(eventApplicantRepository.save(any(EventApplicant.class))).thenReturn(eventApplicant);

        ResponseEntity<?> response = eventService.addApplicantById(1, 1);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(200);
        assertEquals(expectedCode, actualCode);
    }
    @Test
    public void test_addApplicantById_failedEventNotFound(){
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        ResponseEntity<?> response = eventService.addApplicantById(1, 1);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(404);
        assertEquals(expectedCode, actualCode);
    }
    @Test
    public void test_addApplicantById_failedNoUser(){
        Event event = new Event(1, "Text", "Image.png");
        when(eventRepository.findById(any(Integer.class))).thenReturn(Optional.of(event));

        ResponseEntity<?> response = eventService.addApplicantById(1, 0);

        HttpStatusCode actualCode = response.getStatusCode();
        HttpStatusCode expectedCode = HttpStatusCode.valueOf(400);
        assertEquals(expectedCode, actualCode);

        if(response.getBody() instanceof String actualError) {
            assertEquals("no user", actualError);
        }
    }
}
