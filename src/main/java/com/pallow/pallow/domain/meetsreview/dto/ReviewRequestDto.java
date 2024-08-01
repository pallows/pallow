package com.pallow.pallow.domain.meetsreview.dto;

import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    private String content;

    private int starRating;
}
