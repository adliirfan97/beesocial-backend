package com.beesocial.contentmanagementservice;

import com.beesocial.contentmanagementservice.model.Content;
import com.beesocial.contentmanagementservice.repository.ContentRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class DatabaseLoader implements ApplicationRunner {

    private final ContentRepository contentRepository;

    public DatabaseLoader(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Content content1 = new Content(
                "Code review",
                "content-management-service/src/main/resources/static/images/merge_conflict.jpg",
                null,
                1,
                "Harry",
                "Potter",
                null
        );

        contentRepository.save(content1);
    }

}
