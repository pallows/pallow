package com.pallow.pallow.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    //MAIL
    MAIL_MISMATCH_OR_CODE_FORBIDDEN(HttpStatus.FORBIDDEN, "입력한 이메일과 다르거나, 코드가 일치하지 않습니다."),

    //TOKEN
    TOKEN_MISMATCH(HttpStatus.NOT_FOUND, "리프레쉬 토큰이 일치하지 않습니다."),
    TOKEN_CHECK_INVALID(HttpStatus.FORBIDDEN, "JWT 토큰을 확인할 수 없거나, 유효하지 않습니다."),
    TOKEN_CHECK_EXPIRED(HttpStatus.FORBIDDEN, "토큰이 만료되었습니다."),
    // user
    DUPLICATE_ACCOUNT_ID(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    USER_ALREADY_DELETED(HttpStatus.FORBIDDEN, "이미 탈퇴한 유저입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    NOT_FOUND_USER_BOARD(HttpStatus.NOT_FOUND, "유저 게시글을 찾을 수 없습니다."),
    NOT_FOUND_USER_BOARD_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    NOT_AUTHORIZED_TO_DELETE_ACCOUNT(HttpStatus.FORBIDDEN, "회원 탈퇴는 본인만 할 수 있습니다."),

    NOT_FOUND_GROUP(HttpStatus.NOT_FOUND, "해당 모임을 찾을 수 없습니다."),
    NOT_FOUND_USER_IN_GROUP(HttpStatus.NOT_FOUND, "해당 모임에 유저를 찾을 수 없습니다."),
    ALREADY_APPLIED_GROUP(HttpStatus.CONFLICT, "이미 해당 모임에 참여 신청을 보냈습니다."),
    NOT_FOUND_APPLY(HttpStatus.NOT_FOUND, "해당 신청을 찾을 수 없습니다."),

    UNAPPROVED_USER(HttpStatus.LOCKED, "승인되지 않은 사용자입니다."),
    NOT_AUTHORIZED_UPDATE(HttpStatus.LOCKED, "이 게시물을 수정할 권한이 없습니다."),
    NOT_AUTHORIZED_DELETE(HttpStatus.LOCKED, "이 게시물을 삭제할 권한이 없습니다."),

    // Auth
    INVALID_USERNAME(HttpStatus.EXPECTATION_FAILED, "유효하지 않은 아이디입니다."),
    INVALID_PASSWORD(HttpStatus.EXPECTATION_FAILED, "유효하지 않은 비밀번호입니다."),
    LOGIN_FAILED(HttpStatus.EXPECTATION_FAILED, "로그인에 실패하셨습니다."),

    // MeetsReview
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    INVALID_REVIEW_FOR_MEET(HttpStatus.UNAUTHORIZED, "해당 리뷰가 특정 모임에 유효하지 않다"),

    // Profile
    USER_MISMATCH_ID(HttpStatus.CONFLICT, "회원 번호가 일치하지 않습니다."),

    // InvitedBoard
    NOT_GROUP_CREATOR(HttpStatus.FORBIDDEN, "모임장이 아닙니다."),
    YES_GROUP_CREATOR(HttpStatus.CONFLICT, "모임장은 모임 신청을 할 수 없습니다."),
    MAX_MEMBER_REACHED(HttpStatus.BAD_REQUEST, "신청인원이 다 찼습니다."),

    // UserBoard
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "해당 게시물을 찾을 수 없습니다."),

    // Likes
    NOT_FOUND_CONTENT(HttpStatus.NOT_FOUND, "컨텐츠를 찾을 수 없습니다");


    private final HttpStatus status;

    private final String message;
}
