package com.pallow.pallow.domain.meetsreview.dto;

import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private Long id;

    private String content;

    private int starRating;

    private int likesCount;

    private String reviewerName;

//    private String reviewerProfilePhoto;

    public ReviewResponseDto(MeetsReview meetsReview) {
        this.id = meetsReview.getId();
        this.content = meetsReview.getContent();
        this.likesCount = meetsReview.getLikesCount();
        this.starRating = meetsReview.getStarRating();
        this.reviewerName = meetsReview.getUser().getUsername(); // 유저 이름
//        this.reviewerProfilePhoto = meetsReview.getUser().getProfile().getPhoto(); // 프로필 사진
    }
}
