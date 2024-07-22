package com.pallow.pallow.domain.meetsreview.dto;

import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private Long id;

    private String content;

    public ReviewResponseDto(MeetsReview meetsReview) {
        this.id = meetsReview.getId();
        this.content = meetsReview.getContent();
    }
}
