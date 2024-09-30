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
    @JoinColumn(name = "FK_userId")
    private int userId;
    private String text;
    private String image;
    private LocalDateTime timeStamp;
    @JoinColumn(name = "FK_repostId")
    private UUID repostId;

    public Content( int userId, String text, String image, UUID repostId) {
        this.contentId = UUID.randomUUID();
        this.userId = userId;
        this.text = text;
        this.image = image;
        this.timeStamp = LocalDateTime.now();
        this.repostId = repostId;
    }

}
