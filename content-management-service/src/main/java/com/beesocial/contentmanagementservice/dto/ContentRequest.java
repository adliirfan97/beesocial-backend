package com.beesocial.contentmanagementservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

//@AllArgsConstructor
//@Getter
//@Setter
//public record ContentRequest (
//        @NotNull(message = "User required")
//        int userId,
////        @NotNull(message = "Message cannot be empty")
//        String text,
////        @NotNull(message = "Minimum of one image is required")
//        String image,
//        UUID repostId
//) {
//
//
//}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentRequest {

        @NotNull(message = "User required")
        private int userId;
        private String text;
        private String image;
        private UUID repostId;
}