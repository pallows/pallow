package com.pallow.pallow.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Message {

    //SOCIAL OAUTH
    OAUTH_LOGIN_NOT_IMPLEMENTED(HttpStatus.OK, "로그인 성공하였습니다."),
    USER_OAUTH_SIGNUP_SUCCESS(HttpStatus.OK, "소셜 회원가입에 성공하였습니다."),

    //MAIL
    MAIL_SEND_SUCCESS(HttpStatus.OK, "인증 번호가 전송되었습니다."),
    MAIL_VERIFICATION_CODE_SUCCESS(HttpStatus.OK, "이메일 인증이 완료 되었습니다."),

    //TOKEN
    TOKEN_CREATE_REFRESH(HttpStatus.OK, "Refresh Token 재발급하였습니다."),

    // USER
    USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인 ! 완료 ❤️."),
    USER_LOGIN_FAIL(HttpStatus.OK, "로그인 실패! ❤️."),
    USER_LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 되었습니다."),
    USER_DELETE_SUCCESS(HttpStatus.OK, "회원 정보를 삭제하였습니다."),
    USER_READ_ALL_SUCCESS(HttpStatus.OK, "회원 전체 조회를 하였습니다."),
    USER_UPDATE_SUCCESS(HttpStatus.OK, "회원 정보를 수정하였습니다."),
    USER_LOCAL_SIGNUP_SUCCESS(HttpStatus.OK, "LOCAL 회원 가입에 성공하였습니다."),
    USER_INFO_SUCCESS(HttpStatus.OK, "회원 정보 성공적으로 확인"),

    // PROFILE
    PROFILE_READ_SUCCESS(HttpStatus.OK, "프로필을 조회하였습니다."),
    PROFILE_CREATE_SUCCESS(HttpStatus.OK, "프로필을 생성하였습니다."),
    PROFILE_UPDATE_SUCCESS(HttpStatus.OK, "프로필을 수정하였습니다."),
    PROFILE_DELETE_SUCCESS(HttpStatus.OK, "프로필을 삭제하였습니다."),
    PROFILE_RECOMMENDATION_SUCCESS(HttpStatus.OK, "랜덤 프로필 추천"),

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
    INVITATION_READ_SUCCESS(HttpStatus.OK, "신청 목록을 불러왔습니다."),


    // group(meets)
    MEET_CREATE_SUCCESS(HttpStatus.OK, "그룹을 생성하였습니다."),
    MEET_READ_SUCCESS(HttpStatus.OK, "그룹을 조회하였습니다."),
    MEET_UPDATE_SUCCESS(HttpStatus.OK, "그룹을 수정하였습니다."),
    MEET_DELETE_SUCCESS(HttpStatus.OK, "그룹을 삭제하였습니다."),
    MEET_MEMBER_READ_SUCCESS(HttpStatus.OK, "그룹의 전체 회원을 조회하였습니다."),
    MEET_WITHDRAW_MEMBER_SUCCESS(HttpStatus.OK, "회원을 성공적으로 퇴장시켰습니다."),


    // meetsReview
    REVIEW_CREATE_SUCCESS(HttpStatus.OK, "리뷰를 생성하였습니다."),
    REVIEW_READ_SUCCESS(HttpStatus.OK, "리뷰를 조회하였습니다."),
    REVIEW_UPDATE_SUCCESS(HttpStatus.OK, "리뷰를 수정하였습니다."),
    REVIEW_DELETE_SUCCESS(HttpStatus.OK, "리뷰를 삭제하였습니다."),

    //chatroom
    ROOM_CREATE_SUCCESS(HttpStatus.OK, "채팅방을 생성하였습니다."),
    ROOM_READ_SUCCESS(HttpStatus.OK, "채팅방을 조회하였습니다."),
    ROOM_ENTER_SUCCESS(HttpStatus.OK, "채팅방에 입장하였습니다."),
    ROOM_DELETE_SUCCESS(HttpStatus.OK, "채팅방을 삭제하였습니다"),
    INVITATION_SENT_SUCCESS(HttpStatus.OK, "채팅방 초대 보내기를 성공하였습니다."),
    INVITATION_ACCEPTED_SUCCESS(HttpStatus.OK, "채팅방 초대받기를 성공하였습니다."),

    //chatmessage
    MESSAGE_CREATE_SUCCESS(HttpStatus.OK, "메시지를 생성하였습니다."),

    //message reaction
    LIKES_TOGGLE_SUCCESS(HttpStatus.OK, "좋아요 생성에 성공하였습니다."),


    // Likes
    LIKES_CREATE_SUCCESS(HttpStatus.OK, "리뷰를 생성하였습니다.");


    private final HttpStatus status;
    private final String message;
}
