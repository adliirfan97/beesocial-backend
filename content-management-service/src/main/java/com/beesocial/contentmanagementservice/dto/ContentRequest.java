package com.beesocial.contentmanagementservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ContentRequest (
        @NotNull(message = "User required")
        int userId,
//        @NotNull(message = "Message cannot be empty")
        String text,
//        @NotNull(message = "Minimum of one image is required")
        String image,
        UUID repostId
) {


}