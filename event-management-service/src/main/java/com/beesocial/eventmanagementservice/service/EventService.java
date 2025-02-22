package com.beesocial.eventmanagementservice.service;

import com.beesocial.eventmanagementservice.feign.UserManagementClient;
import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.model.EventApplicant;
import com.beesocial.eventmanagementservice.model.ROLE;
import com.beesocial.eventmanagementservice.model.UserDTO;
import com.beesocial.eventmanagementservice.repository.EventApplicantRepository;
import com.beesocial.eventmanagementservice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventApplicantRepository eventApplicantRepository;
    @Autowired
    private UserManagementClient userManagementClient;

    public String removeSpaceFromText(String text){
        return text.replaceAll("[^a-zA-Z0-9\\p{Punct}]", "");
    }
    public boolean isImageCorrectFormat(String imagePath){
        String[] validExtensions = {".jpg", ".png", ".jpeg"};
        String imagePathLowerCase = imagePath.toLowerCase();
        for(String extension : validExtensions){
            if(imagePathLowerCase.endsWith(extension)){
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<?> saveEvent (Event event){
        if(event == null){
            return ResponseEntity.badRequest().body("event is null");
        }
        if(event.getUserId() <=0){
            return ResponseEntity.badRequest().body("no host");
        }
        Optional<UserDTO> userDTOOptional = userManagementClient.getUserById(event.getUserId());
        if(userDTOOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = userDTOOptional.get();
        if(userDTO.getRole()!= ROLE.HR){
            return ResponseEntity.badRequest().body("user is not HR");
        }
        String text = event.getText();
        String image = event.getImage();
        if((text == null || removeSpaceFromText(text).isEmpty()) && (image == null || image.trim().isEmpty())){
            return ResponseEntity.badRequest().body("no image and no text");
        }
        if (!(text ==null || text.isEmpty())){
            String textNoSpace = removeSpaceFromText(text);
            if (textNoSpace.length() >= 2000){
                return ResponseEntity.badRequest().body("text more than 2000 characters");
            }
        }
        if (!(image == null || image.trim().isEmpty())){
            if(!isImageCorrectFormat(image.trim())){
                return ResponseEntity.badRequest().body("image format not supported");
            }
        }
        return ResponseEntity.ok(eventRepository.save(event));
    }

    public ResponseEntity<?> editEventById(int id, Event updatedEvent){
        Optional<Event> eventOptional = eventRepository.findById(id);
        if(eventOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Event event = eventOptional.get();
        if(updatedEvent.getUserId() <=0){
            return ResponseEntity.badRequest().body("no host");
        }
        Optional<UserDTO> userDTOOptional = userManagementClient.getUserById(updatedEvent.getUserId());
        if(userDTOOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = userDTOOptional.get();
        if(userDTO.getRole()!=ROLE.HR){
            return ResponseEntity.badRequest().body("user is not HR");
        }
        String text = updatedEvent.getText();
        String image = updatedEvent.getImage();
        if((text == null || removeSpaceFromText(text).isEmpty()) && (image == null || image.trim().isEmpty())){
            return ResponseEntity.badRequest().body("no image and no text");
        }
        if (!(text ==null || text.isEmpty())){
            String textNoSpace = removeSpaceFromText(text);
            if (textNoSpace.length() >= 2000){
                return ResponseEntity.badRequest().body("text more than 2000 characters");
            }
        }
        if (!(image == null || image.trim().isEmpty())){
            if(!isImageCorrectFormat(image.trim())){
                return ResponseEntity.badRequest().body("image format not supported");
            }
        }
        event.setUserId(updatedEvent.getUserId());
        event.setText(updatedEvent.getText());
        event.setImage(updatedEvent.getImage());
        event.setTimestamp(LocalDateTime.now());
        event.setEdited(true);
        return ResponseEntity.ok(eventRepository.save(event));
    }

    public ResponseEntity<?> addApplicantById(int eventId, int userId){
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Optional<UserDTO> userDTOOptional = userManagementClient.getUserById(userId);
        List<EventApplicant> eventApplicants = eventApplicantRepository.findAll();
        if(!eventApplicants.isEmpty()){
            List<Integer> usersEnrolled = eventApplicants.stream()
                    .filter((ea)->ea.getEventId()==eventId)
                    .map((ea)->ea.getUserId())
                    .toList();
            if(usersEnrolled.contains(userId)){
                return ResponseEntity.badRequest().body("user already applied");
            }
        }
        if(eventOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(userDTOOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        EventApplicant eventApplicant = new EventApplicant(eventId, userId);
        return ResponseEntity.ok(eventApplicantRepository.save(eventApplicant));
    }

    public ResponseEntity<?> getAllEvents(){
        List<Event> eventList = eventRepository.findAll();
        return ResponseEntity.ok(eventList.stream()
                .sorted(new Comparator<Event>() {
                    @Override
                    public int compare(Event o1, Event o2) {
                        return o2.getTimestamp().compareTo(o1.getTimestamp());
                    }
                })
                .toList()
        );
    }

    public ResponseEntity<?> getuserById(int id){
        Optional<UserDTO> userDTOOptional = userManagementClient.getUserById(id);
        if(userDTOOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTOOptional.get());
    }
    public ResponseEntity<?> getAppliedById(int eventId){
        return ResponseEntity.ok(eventApplicantRepository.findAll().stream()
                .filter(eventApplicant -> eventApplicant.getEventId()==eventId)
                .map(eventApplicant -> getuserById(eventApplicant.getUserId()).getBody())
                .toList()
        );
    }
    public void deleteById(int id){
        eventRepository.deleteById(id);
    }

//
//        public ResponseEntity<Object> saveEvent(Event event){
//        String text = event.getText();
//        String image = event.getImage();
//        if(event.getUserId() == null || event.getUserId().isEmpty()){
//            return ResponseEntity.badRequest().body("no host for event");
//        }
//        if((text == null || text.isEmpty()) && (image == null || image.isEmpty())){
//            return ResponseEntity.badRequest().body("no text and no image");
//        }
//        if(!(text==null||text.isEmpty())){
//            if(text.length()>=2000){
//                return ResponseEntity.badRequest().body("text too long");
//            }
//            event.setText(text);
//        }
//
//        Map<String, Object> eventMap = new HashMap<>();
//        eventMap.put("userId", event.getUserId());
//        eventMap.put("text", event.getText());
//        eventMap.put("image", event.getImage());
//        eventMap.put("timestamp", event.getTimestamp());
//        eventMap.put("isEdited", event.isEdited());
//
//        // ADD: save event to database
//        return ResponseEntity.ok(eventMap);
//    }
//    public ResponseEntity<Object> addApplicant(EventApplicant eventApplicant){
//        String eventId = eventApplicant.getEventId();
//        String userId = eventApplicant.getUserId();
//        if(eventId==null || userId==null || eventId.isEmpty()||userId.isEmpty()){
//            return ResponseEntity.badRequest().body("no userId or eventId");
//        }
//        Map<String, Object> eventApplicantMap = new HashMap<>();
//        eventApplicantMap.put("eventId", eventId);
//        eventApplicantMap.put("userId", userId);
//        eventApplicantMap.put("timestamp", eventApplicant.getTimestamp());
//
//        return ResponseEntity.ok(eventApplicantMap);
//    }
//    public ResponseEntity<Object> editEvent(Event event){
//        String text = event.getText();
//        String image = event.getImage();
//        if(event.getUserId() == null || event.getUserId().isEmpty()){
//            return ResponseEntity.badRequest().body("no host for event");
//        }
//        if((text == null || text.isEmpty()) && (image == null || image.isEmpty())){
//            return ResponseEntity.badRequest().body("no text and no image");
//        }
//        if(!(text==null||text.isEmpty())){
//            if(text.length()>=2000){
//                return ResponseEntity.badRequest().body("text too long");
//            }
//            event.setText(text);
//        }
//
//        Map<String, Object> eventMap = new HashMap<>();
//        eventMap.put("userId", event.getUserId());
//        eventMap.put("text", event.getText());
//        eventMap.put("image", event.getImage());
//        eventMap.put("timestamp", event.getTimestamp());
//        eventMap.put("isEdited", true);
//
//        // ADD: save event to database
//        return ResponseEntity.ok(eventMap);
//    }
}
