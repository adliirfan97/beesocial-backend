package com.beesocial.opportunitymanagementservice.service;

import com.beesocial.opportunitymanagementservice.dto.OpportunityRequest;
import com.beesocial.opportunitymanagementservice.model.Opportunity;
import com.beesocial.opportunitymanagementservice.feign.FirebaseClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpportunityService {

    private final FirebaseClient firebaseClient;
    private final ObjectMapper objectMapper;


    public OpportunityService(FirebaseClient firebaseClient, ObjectMapper objectMapper) {
        this.firebaseClient = firebaseClient;
        this.objectMapper = objectMapper;

    }

    public String createOpportunity(OpportunityRequest opportunityRequest) {
        Opportunity opportunity = new Opportunity(
                opportunityRequest.userId(),
                opportunityRequest.text(),
                opportunityRequest.image(),
                System.currentTimeMillis());

        Map<String,Object> opportunityMap  = objectMapper.convertValue(opportunity, Map.class);

        return firebaseClient.createOpportunity(opportunityMap);
    }

    public String getAll() {
        return firebaseClient.getAll();
    }

    public String getOpportunity(String documentId) {
        return firebaseClient.getOpportunity(documentId);
    }

    public String updateOpportunity(String documentId, OpportunityRequest opportunityRequest) {
        Opportunity opportunity = new Opportunity(
                documentId,
                opportunityRequest.userId(),
                opportunityRequest.text(),
                opportunityRequest.image(),
                System.currentTimeMillis());

        Map<String,Object> opportunityMap  = objectMapper.convertValue(opportunity, Map.class);
        return firebaseClient.updateOpportunity(documentId, opportunityMap);

    }

    public String deleteOpportunity(String documentId) {
        return firebaseClient.deleteOpportunity(documentId);
    }
}
