package com.beesocial.opportunitymanagementservice.service;

import com.beesocial.opportunitymanagementservice.dto.OpportunityRequest;
import com.beesocial.opportunitymanagementservice.model.Opportunity;
import com.beesocial.opportunitymanagementservice.repository.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class OpportunityService {

    @Autowired
    private OpportunityRepository opportunityRepository;


    public Opportunity createOpportunity(OpportunityRequest opportunityRequest) {
        LocalDateTime currentTimestamp = LocalDateTime.now();

        Opportunity opportunity = new Opportunity(
                opportunityRequest.userId(),
                opportunityRequest.text(),
                opportunityRequest.url(),
                currentTimestamp
        );
        return opportunityRepository.save(opportunity);
    }

    public String getAll() {
        return null;
    }

    public String getOpportunity(int opportunityId) {
        return null;
    }

    public String updateOpportunity(int opportunityId, OpportunityRequest opportunityRequest) {
        return null;

    }

    public String deleteOpportunity(int opportunityId) {
        return null;
    }
}
