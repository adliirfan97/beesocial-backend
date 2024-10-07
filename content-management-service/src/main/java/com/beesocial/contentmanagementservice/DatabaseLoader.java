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
                "hehe",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRyfY8XFqWAcMopn2U-IdjW7XZj76E0qeIwUA&s",
                null,
                1,
                "Harry",
                "Potter",
                null
        );

        contentRepository.save(content1);
    }

}
