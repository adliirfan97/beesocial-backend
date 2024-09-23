package com.beesocial.contentmanagementservice.service;

import com.beesocial.contentmanagementservice.dto.ContentRequest;
import com.beesocial.contentmanagementservice.feign.FirebaseClient;
import com.beesocial.contentmanagementservice.model.Content;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ContentService {

    private final FirebaseClient firebaseClient;
    private final ObjectMapper objectMapper;

    public ContentService(FirebaseClient firebaseClient, ObjectMapper objectMapper) {
        this.firebaseClient = firebaseClient;
        this.objectMapper = objectMapper;
    }

    public String createContent(ContentRequest contentRequest) {
        Content content = new Content(
                contentRequest.userId(),
                contentRequest.text(),
                contentRequest.image(),
                System.currentTimeMillis(),
                contentRequest.repostId()
        );

        Map<String,Object> contentMap  = objectMapper.convertValue(content, Map.class);

        return firebaseClient.createContent(contentMap);
    }

    public String getAll() {return firebaseClient.getAll();}
}
