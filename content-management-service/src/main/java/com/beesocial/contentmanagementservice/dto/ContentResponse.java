package com.beesocial.contentmanagementservice.dto;

import com.beesocial.contentmanagementservice.model.Content;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentResponse {

    private UUID contentId;
    private int userId;
    private String firstName;
    private String lastName;
    private String profilePhoto;
    private String text;
    private String image;
    private LocalDateTime timeStamp;
    private UUID repostId;
}
