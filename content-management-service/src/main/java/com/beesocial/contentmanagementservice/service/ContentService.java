package com.beesocial.contentmanagementservice.service;

import com.beesocial.contentmanagementservice.dto.ContentRequest;
import com.beesocial.contentmanagementservice.dto.ContentResponse;
import com.beesocial.contentmanagementservice.dto.UserResponse;
import com.beesocial.contentmanagementservice.feign.FirebaseClient;
import com.beesocial.contentmanagementservice.feign.UserManagementClient;
import com.beesocial.contentmanagementservice.model.Content;
import com.beesocial.contentmanagementservice.repository.ContentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentService {

    private final FirebaseClient firebaseClient;
    private final ObjectMapper objectMapper;
    private final ContentRepository contentRepository;
    private final WebClient.Builder webClientBuilder;
    private final UserManagementClient userManagementClient;

//    @Autowired
//    public ContentService(FirebaseClient firebaseClient,
//                          ObjectMapper objectMapper,
//                          ContentRepository contentRepository) {
//        this.firebaseClient = firebaseClient;
//        this.objectMapper = objectMapper;
//        this.contentRepository = contentRepository;
//    }

    public ContentResponse getContentById(UUID contentId) {

        Optional<Content> contentOptional = contentRepository.findById(contentId);

        if (contentOptional.isEmpty()) {
            throw new NoSuchElementException(STR."Content with id: \{contentId} could not be found.");
        }

        Content contentInDB = contentOptional.get();

        int userId = contentInDB.getUserId();
        // find user info in db by userId
//        UserResponse userResponse = webClientBuilder.build().get()
//                .uri(STR."http://user-management-service/user/\{userId}")
//                .retrieve()
//                .bodyToMono(UserResponse.class)
//                .block();

        Optional<UserResponse> userResponseOptional = userManagementClient.getUserById(userId);

        if (userResponseOptional.isEmpty()) {
            throw new NoSuchElementException(STR."User with id: \{userId} could not be found.");
        }

        String firstName = userResponseOptional.get().getFirstName();
        String lastName = userResponseOptional.get().getLastName();
        String profilePhoto = userResponseOptional.get().getProfilePhoto();

        return new ContentResponse(
                contentInDB.getContentId(),
                userId, firstName, lastName, profilePhoto,
                contentInDB.getText(),
                contentInDB.getImage(),
                contentInDB.getTimeStamp(),
                contentInDB.getRepostId()
                );
    }

    public List<Content> getAllContent() {

        return contentRepository.findAll();
    }

    public Content createContent(ContentRequest contentRequest) {

        Content content = new Content(
                contentRequest.userId(),
                contentRequest.text(),
                contentRequest.image(),
                contentRequest.repostId()
        );

        contentRepository.save(content);

        return content;
    }

}
