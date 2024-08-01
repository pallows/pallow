package com.pallow.pallow.domain.meetsreview.entity;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.meetsreview.dto.ReviewRequestDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.entity.TimeStamp;
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
public class MeetsReview extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int likesCount;

    /**
     * 유지영
     * StarRating entity 추가
     */
    private int starRating;

    @ManyToOne
    @JoinColumn
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

    public MeetsReview update(ReviewRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.starRating  = requestDto.getStarRating();
        return this;
    }

    public void addLikesCount() {
        this.likesCount++;
    }

    public void minusLikesCount() {
        this.likesCount--;
    }
}
