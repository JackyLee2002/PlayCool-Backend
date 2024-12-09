package org.codeplay.playcoolbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "vote")
@Table(name = "vote", indexes = {
        @Index(name = "idx_userId", columnList = "userId"),
        @Index(name = "idx_songId", columnList = "songId")
})
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long songId;

    private Long userId;
}
