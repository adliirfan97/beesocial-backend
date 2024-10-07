package com.beesocial.contentmanagementservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Content {
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID contentId;
    private String text;
    private String image;
    private LocalDateTime timeStamp;
    @ManyToOne
    @JoinColumn(name = "FK_repostId")
    private Content repostedContent;
    @JoinColumn(name = "FK_userId")
    private int userId;
    private String firstName;
    private String lastName;
    private String profilePhoto;


    public Content(String text,
                   String image,
                   Content repostedContent,
                   int userId,
                   String firstName,
                   String lastName,
                   String profilePhoto) {
        this.contentId = UUID.randomUUID();
        this.text = text;
        this.image = image;
        this.timeStamp = LocalDateTime.now();
        this.repostedContent = repostedContent;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhoto = profilePhoto;
    }

}
