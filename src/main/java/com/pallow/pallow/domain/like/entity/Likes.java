package com.pallow.pallow.domain.like.entity;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.entity.TimeStamp;
import com.pallow.pallow.global.enums.ContentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Likes extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @Column(nullable = false)
    private Long contentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Likes(ContentType contentType, Long contentId, User user) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.user = user;
    }
}