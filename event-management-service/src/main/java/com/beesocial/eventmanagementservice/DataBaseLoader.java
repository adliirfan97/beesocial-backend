package com.beesocial.eventmanagementservice;

import com.beesocial.eventmanagementservice.feign.UserManagementClient;
import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.model.ROLE;
import com.beesocial.eventmanagementservice.model.UserDTO;
import com.beesocial.eventmanagementservice.repository.EventRepository;
import feign.RetryableException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class DataBaseLoader implements ApplicationRunner {
    private EventRepository eventRepository;
    private UserManagementClient userManagementClient;

    public DataBaseLoader(EventRepository eventRepository, UserManagementClient userManagementClient) {
        this.eventRepository = eventRepository;
        this.userManagementClient = userManagementClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserDTO userDTO = new UserDTO(1, "John", "Doe", "johndoe@email.com", "90909090", "password", "profileImage.png", ROLE.HR);
        UserDTO userDTO2 = new UserDTO(2, "John", "Doe", "johndoe@email.com", "90909090", "password", "profileImage.png", ROLE.EMPLOYEE);
        userManagementClient.saveNewUser(userDTO);
        userManagementClient.saveNewUser(userDTO2);

        Event event = new Event(1, "Event happening on the 32/09/2024", "image/path/here.png");
        Event event2 = new Event(1, "Event happening on the 33/09/2024", null);
        eventRepository.save(event);
        eventRepository.save(event2);
    }
}
