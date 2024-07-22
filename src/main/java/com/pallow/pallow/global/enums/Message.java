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
    COMMENT_READ_SUCCESS(HttpStatus.OK, "댓글을 조회하였습니다.");


    private final HttpStatus status;
    private final String message;
}
