package com.pallow.pallow.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Message {

    // PROFILE
    PROFILE_READ_SUCCESS(HttpStatus.OK, "프로필을 조회하였습니다."),
    PROFILE_CREATE_SUCCESS(HttpStatus.OK, "프로필을 생성하였습니다."),
    PROFILE_UPDATE_SUCCESS(HttpStatus.OK, "프로필을 수정하였습니다."),
    PROFILE_DELETE_SUCCESS(HttpStatus.OK, "프로필을 삭제하였습니다."),

    // USER BOARD
    USERBOARD_CREATE_SUCCESS(HttpStatus.OK, "유저 게시글을 생성하였습니다."),
    USERBOARD_UPDATE_SUCCESS(HttpStatus.OK, "유저 게시글을 수정하였습니다."),
    USERBOARD_DELETE_SUCCESS(HttpStatus.OK, "유저 게시글을 삭제하였습니다."),
    USERBOARD_READ_SUCCESS(HttpStatus.OK, "유저 게시글을 조회하였습니다."),

    // COMMENT
    COMMENT_CREATE_SUCCESS(HttpStatus.OK, "댓글을 생성하였습니다."),
    COMMENT_UPDATE_SUCCESS(HttpStatus.OK, "댓글을 수정하였습니다"),
    COMMNET_DELETE_SUCCESS(HttpStatus.OK, "댓글을 삭제하였습니다."),
    COMMENT_READ_SUCCESS(HttpStatus.OK, "댓글을 조회하였습니다."),

    // INVITED BOARD
    APPLY_FOR_GROUP_SUCCESS(HttpStatus.OK, "성공적으로 가입 신청을 보냈습니다."),
    ACCEPT_APPLY_SUCCESS(HttpStatus.OK, "가입 신청을 수락했습니다."),
    DECLINE_APPLY_SUCCESS(HttpStatus.OK, "가입 신청을 거절했습니다."),

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
