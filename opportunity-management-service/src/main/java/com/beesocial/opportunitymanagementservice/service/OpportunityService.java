package com.beesocial.opportunitymanagementservice.service;

import com.beesocial.opportunitymanagementservice.dto.OpportunityRequest;
import com.beesocial.opportunitymanagementservice.model.Opportunity;
import com.beesocial.opportunitymanagementservice.repository.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


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

    public List<Opportunity> getAll() {
        return opportunityRepository.findAll();
    }

    public Opportunity getOpportunity(int opportunityId) {
        return opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));
    }

    public Opportunity updateOpportunity(int opportunityId, OpportunityRequest opportunityRequest) {
        Opportunity opportunity = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        opportunity.setUserId(opportunityRequest.userId());
        opportunity.setText(opportunityRequest.text());
        opportunity.setUrl(opportunityRequest.url());
        opportunity.setTimeStamp(LocalDateTime.now());

        return opportunityRepository.save(opportunity);
    }

    public void deleteOpportunity(int opportunityId) {
        Opportunity opportunity = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        opportunityRepository.delete(opportunity);
    }

    @Transactional
    public void applyOpportunity(int opportunityId, Integer applicantId) {
        Opportunity opportunity = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        System.out.println("Before adding: " + opportunity.getApplicantIds());
        if (applicantId == null) {
            System.out.println("Applicant ID is null, cannot add to list.");
        } else {
            opportunity.addApplicant(applicantId);
        }
        System.out.println("After adding: " + opportunity.getApplicantIds());
        for (Integer ap : opportunity.getApplicantIds()) {
            System.out.println(ap);
        }
        opportunityRepository.save(opportunity);
    }
}

