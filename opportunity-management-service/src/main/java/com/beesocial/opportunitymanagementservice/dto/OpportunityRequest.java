package com.beesocial.opportunitymanagementservice.dto;


import jakarta.validation.constraints.NotNull;

public record OpportunityRequest (
        @NotNull(message = "User required")
        String userId,
        @NotNull(message = "Message cannot be empty")
        String text,
        @NotNull(message = "Minimum of one image is required")
        String image
) {


}

