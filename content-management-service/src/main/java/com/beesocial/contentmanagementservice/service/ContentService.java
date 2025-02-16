package com.beesocial.contentmanagementservice.service;

import com.beesocial.contentmanagementservice.dto.ContentRequest;
import com.beesocial.contentmanagementservice.dto.UserResponse;
import com.beesocial.contentmanagementservice.feign.UserManagementClient;
import com.beesocial.contentmanagementservice.model.Content;
import com.beesocial.contentmanagementservice.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentService {

    private final ContentRepository contentRepository;
    private final UserManagementClient userManagementClient;

    public Content getContentById(UUID contentId) {

        Optional<Content> contentOptional = contentRepository.findById(contentId);

        if (contentOptional.isEmpty()) {
            throw new NoSuchElementException("Content with id: " + contentId + " could not be found.");
        }

        Content contentInDB = contentOptional.get();

        // find user info in db by userId
        Optional<UserResponse> userResponseOptional = userManagementClient.getUserById(contentInDB.getUserId());

        if (userResponseOptional.isEmpty()) {
            throw new NoSuchElementException("User with id: " + contentInDB.getUserId() + " could not be found.");
        }

        String firstName = userResponseOptional.get().getFirstName();
        String lastName = userResponseOptional.get().getLastName();
        String profilePhoto = userResponseOptional.get().getProfilePhoto();

//        return new ContentResponse(
//                contentInDB.getContentId(),
//                contentInDB.getUserId(), firstName, lastName, profilePhoto,
//                contentInDB.getText(),
//                contentInDB.getImage(),
//                contentInDB.getTimeStamp(),
//                contentInDB.getRepostedContent().getContentId()
//                );
        return contentInDB;
    }

    public List<Content> getAllContent() {

        return contentRepository.findAll();
    }

    public List<Content> getAllContentFromUser(int userId) {

        return contentRepository.findAllByUserId(userId);
    }

    public Content createContent(ContentRequest contentRequest) {

        // find user info in db by userId
        Optional<UserResponse> userResponseOptional = userManagementClient.getUserById(contentRequest.getUserId());

        if (userResponseOptional.isEmpty()) {
            throw new NoSuchElementException("User with id: " + contentRequest.getUserId()+ " could not be found.");
        }

        String firstName = userResponseOptional.get().getFirstName();
        String lastName = userResponseOptional.get().getLastName();
        String profilePhoto = userResponseOptional.get().getProfilePhoto();


        Content content = new Content(
                contentRequest.getText(),
                contentRequest.getImage(),
                null,
                contentRequest.getUserId(),
                firstName,
                lastName,
                profilePhoto

        );

        if (contentRequest.getRepostId() == null) {
            content.setRepostedContent(null);
        } else {
            Optional<Content> repostedContentOptional = contentRepository.findById(contentRequest.getRepostId());

            if (repostedContentOptional.isEmpty()) {
                throw new NoSuchElementException("Content with id: " + contentRequest.getRepostId() + " could not be found.");
            }

            content.setRepostedContent(repostedContentOptional.get());
        }

        contentRepository.save(content);

        return content;
    }

    public void deleteContent(UUID contentId) {

        Optional<Content> contentOptional = contentRepository.findById(contentId);

        if (contentOptional.isEmpty()) {
            throw new NoSuchElementException("Content with id: " + contentId + " could not be found.");
        }

        contentRepository.delete(contentOptional.get());

    }
}
