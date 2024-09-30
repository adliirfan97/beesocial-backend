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
    private final EventRepository eventRepository;

    public DataBaseLoader(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Event event = new Event(1, "Event happening on the 32/09/2024", "src\\pages\\HomePage\\Events\\images\\event1.png");
        Event event2 = new Event(1, "Event happening on the 33/09/2024", null);
        eventRepository.save(event);
        eventRepository.save(event2);
    }
}
