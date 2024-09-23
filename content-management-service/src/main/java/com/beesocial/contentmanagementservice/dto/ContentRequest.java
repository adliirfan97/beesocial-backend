package com.beesocial.contentmanagementservice.dto;

import jakarta.validation.constraints.NotNull;

public record ContentRequest (
        @NotNull(message = "User required")
        String userId,
        @NotNull(message = "Message cannot be empty")
        String text,
        @NotNull(message = "Minimum of one image is required")
        String image,
        int repostId
) {


}