package com.pallow.pallow.domain.meetsreview.entity;

import com.pallow.pallow.domain.like.entity.Likeable;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.meetsreview.dto.ReviewRequestDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.entity.TimeStamp;
import com.pallow.pallow.global.enums.ContentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class MeetsReview extends TimeStamp implements Likeable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private int likesCount;

    @Column(nullable = false)
    private int starRating;

    @ManyToOne
    @JoinColumn(name = "user_user_Id")
    private User user;

    @ManyToOne
    @JoinColumn
    private Meets meets;

    @Builder
    public MeetsReview(String content, int likesCount, int starRating, Meets meets, User user) {
        this.content = content;
        this.likesCount = likesCount;
        this.starRating = starRating;
        this.meets = meets;
        this.user = user;
    }

    public void update(ReviewRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.starRating = requestDto.getStarRating();
    }

    @Override
    public ContentType contentType() {
        return ContentType.MEETS_REVIEW;
    }

    @Override
    public void addLikesCount() {
        this.likesCount++;
    }

    @Override
    public void minusLikesCount() {
        this.likesCount--;
    }
}
