package com.beesocial.opportunitymanagementservice;

import com.beesocial.opportunitymanagementservice.model.Opportunity;
import com.beesocial.opportunitymanagementservice.repository.OpportunityRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataBaseLoader implements ApplicationRunner {

    private final OpportunityRepository opportunityRepository;

    private DataBaseLoader(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        LocalDateTime time = LocalDateTime.now();
        Opportunity opportunity1 = new Opportunity(12, "Hiring new Software Developer", "/path/toImage", time);
        Opportunity opportunity2 = new Opportunity(14, "Hiring new CEO", "/path/toImage", time);
        Opportunity opportunity3 = new Opportunity(20, "Hiring new Software Support", "/path/toImage", time);

        opportunityRepository.save(opportunity1);
        opportunityRepository.save(opportunity2);
        opportunityRepository.save(opportunity3);

    }
}
