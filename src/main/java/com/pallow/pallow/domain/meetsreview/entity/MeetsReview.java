package com.pallow.pallow.domain.meetsreview.entity;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.meetsreview.dto.ReviewRequestDto;
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

    @ManyToOne
    @JoinColumn
    private Meets meets;

    @Builder
    public MeetsReview(String content, int likesCount, Meets meets) {
        this.content = content;
        this.likesCount = likesCount;
        this.meets = meets;
    }

    public MeetsReview update(ReviewRequestDto requestDto) {
        this.content = requestDto.getContent();
        return this;
    }

    public void addLikesCount() {
        this.likesCount++;
    }
}
