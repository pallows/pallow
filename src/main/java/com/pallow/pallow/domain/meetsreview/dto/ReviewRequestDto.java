package com.pallow.pallow.domain.meetsreview.dto;

import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import lombok.Getter;

@Getter
public class ReviewRequestDto {
    /**
     * 유지영
     * StarRating 추가
     */
    private String content;
    private int starRating;


    public ReviewRequestDto(String content, int starRating) {
        this.content = content;
        this.starRating = starRating;
    }
}
