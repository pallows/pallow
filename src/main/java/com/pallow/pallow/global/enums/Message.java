package com.pallow.pallow.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Message {

    // user
    PROFILE_READ_SUCCESS(HttpStatus.OK, "프로필을 조회하였습니다."),

    // group(meets)
    MEET_CREATE_SUCCESS(HttpStatus.OK, "그룹을 생성하였습니다."),
    MEET_READ_SUCCESS(HttpStatus.OK, "그룹을 조회하였습니다."),
    MEET_UPDATE_SUCCESS(HttpStatus.OK, "그룹을 수정하였습니다."),
    MEET_DELETE_SUCCESS(HttpStatus.OK, "그룹을 삭제하였습니다."),

    // meetsReview
    REVIEW_CREATE_SUCCESS(HttpStatus.OK, "리뷰를 생성하였습니다."),
    REVIEW_READ_SUCCESS(HttpStatus.OK, "리뷰를 조회하였습니다."),
    REVIEW_UPDATE_SUCCESS(HttpStatus.OK, "리뷰를 수정하였습니다."),
    REVIEW_DELETE_SUCCESS(HttpStatus.OK, "리뷰를 삭제하였습니다."),

    // Likes
    LIKES_CREATE_SUCCESS(HttpStatus.OK, "리뷰를 생성하였습니다.");


    private final HttpStatus status;
    private final String message;
}
