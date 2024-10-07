package com.beesocial.eventmanagementservice;

import com.beesocial.eventmanagementservice.feign.UserManagementClient;
import com.beesocial.eventmanagementservice.model.Event;
import com.beesocial.eventmanagementservice.model.EventApplicant;
import com.beesocial.eventmanagementservice.model.ROLE;
import com.beesocial.eventmanagementservice.model.UserDTO;
import com.beesocial.eventmanagementservice.repository.EventApplicantRepository;
import com.beesocial.eventmanagementservice.repository.EventRepository;
import feign.RetryableException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DataBaseLoader implements ApplicationRunner {
    private final EventRepository eventRepository;
    private final EventApplicantRepository eventApplicantRepository;

    public DataBaseLoader(EventRepository eventRepository, EventApplicantRepository eventApplicantRepository) {
        this.eventRepository = eventRepository;
        this.eventApplicantRepository = eventApplicantRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Event event = new Event(2, "Event happening on the 32/09/2024", "event-management-service/src/main/resources/static/images/event1.png");
        Event event2 = new Event(3, "Event happening on the 33/09/2024", null);
        eventRepository.saveAll(Arrays.asList(event, event2));

        EventApplicant eventApplicant = new EventApplicant(1, 3);
        EventApplicant eventApplicant1 = new EventApplicant(1, 1);
        eventApplicantRepository.saveAll(Arrays.asList(eventApplicant, eventApplicant1));
    }
}
