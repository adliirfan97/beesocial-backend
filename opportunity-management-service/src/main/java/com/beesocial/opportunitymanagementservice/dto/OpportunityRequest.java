package com.beesocial.opportunitymanagementservice.dto;


import jakarta.validation.constraints.NotNull;

public record OpportunityRequest (
        @NotNull(message = "User required")
        int userId,
        @NotNull(message = "Message cannot be empty")
        String text,
        @NotNull(message = "Minimum of one image is required")
        String url
) {


}

