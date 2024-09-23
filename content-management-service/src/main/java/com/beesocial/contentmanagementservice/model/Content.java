package com.beesocial.contentmanagementservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Content_generator")
    @SequenceGenerator(name = "Content_generator", sequenceName = "Content_seq", allocationSize = 1)
    private int contentId;
    @ManyToOne
    @JoinColumn(name = "FK_userId")
    private String userId;
    private String text;
    private String image;
    private long timeStamp;
    @ManyToOne
    @JoinColumn(name = "FK_repostId")
    private int repostId;

    public Content( String  userId, String text, String image, long timestamp, int repostId) {
        this.userId = userId;
        this.text = text;
        this.image = image;
        this.timeStamp = timestamp;
        this.repostId = repostId;
    }

}
