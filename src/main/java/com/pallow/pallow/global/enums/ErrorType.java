package com.pallow.pallow.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    /**
     * 예시 DUPLICATE_ACCOUNT_ID(HttpStatus.LOCKED, "이미 아이디가 존재합니다."),
     * NOT_EXISTS_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
     * NOT_FOUND_AUTHENTICATION_INFO(HttpStatus.UNAUTHORIZED, "ID 혹은 PASSWORD가 잘못되었습니다."),
     * NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."), UNAPPROVED_USER(HttpStatus.LOCKED,
     * "승인되지 않은 사용자입니다."), APPROVED_USER(HttpStatus.LOCKED, "이미 승인된 회원입니다."),
     * DEACTIVATED_USER(HttpStatus.LOCKED, "탈퇴한 회원입니다."),
     * INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
     * DEACTIVATE_USER(HttpStatus.BAD_REQUEST, "활성화 되지 않은 유저입니다."),
     * <p>
     * // email NOT_FOUND_AUTH_CODE(HttpStatus.NOT_FOUND, "인증 코드를 찾을 수 없습니다."),
     * WRONG_AUTH_CODE(HttpStatus.LOCKED, "잘못된 인증 코드입니다."), EXPIRED_AUTH_CODE(HttpStatus.LOCKED,
     * "만료된 인증 코드입니다."),
     * <p>
     * // company UNREGISTERED_DOMAIN(HttpStatus.LOCKED, "등록되지 않은 도메인입니다."),
     * <p>
     * // user ALREADY_TALYE_USER(HttpStatus.CONFLICT, "이미 탈퇴했어"),
     * <p>
     * // feed NOT_FOUND_FEED(HttpStatus.LOCKED, "게시물을 찾을 수 없습니다."),
     * NOT_AUTHORIZED_UPDATE(HttpStatus.LOCKED, "이 게시물을 수정할 권한이 없습니다."),
     * NOT_AUTHORIZED_DELETE(HttpStatus.LOCKED, "이 게시물을 삭제할 권한이 없습니다."),
     * <p>
     * // reply NOT_FOUND_REPLY(HttpStatus.NOT_FOUND, "해당하는 댓글이 없습니다."),
     * WRONG_USER_REPLY(HttpStatus.LOCKED, "사용자가 쓴 댓글이 아닙니다."),
     * <p>
     * // likes SAME_USER_FEED(HttpStatus.LOCKED, "본인 게시글엔 누를 수 없습니다."),
     * SAME_USER_REPLY(HttpStatus.LOCKED, "본인 댓글엔 누를 수 없습니다."), NOT_FOUND_LIKE(HttpStatus.NOT_FOUND,
     * "좋아요 정보를 찾을 수 없습니다."), DUPLICATE_LIKE(HttpStatus.LOCKED, "좋아요 중복 등록"),
     * <p>
     * // follow CANNOT_FOLLOW_MYSELF(HttpStatus.FORBIDDEN, "자기 자신을 팔로우 할 수 없습니다."),
     * DUPLICATE_FOLLOW(HttpStatus.LOCKED, "이미 팔로우한 회원입니다."), NOT_FOLLOWING(HttpStatus.LOCKED, "팔로우
     * 한 상태가 아닙니다."),
     * <p>
     * // admin NOT_EXIST_ALL_USERS(HttpStatus.BAD_REQUEST, "회원이 존재하지 않습니다.");
     */

    // user
    DUPLICATE_ACCOUNT_ID(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    NOT_FOUND_USER_BOARD(HttpStatus.NOT_FOUND, "유저 게시글을 찾을 수 없습니다."),
    NOT_FOUND_USER_BOARD_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),

    NOT_FOUND_GROUP(HttpStatus.NOT_FOUND, "해당 모임을 찾을 수 없습니다."),
    NOT_FOUND_USER_IN_GROUP(HttpStatus.NOT_FOUND, "해당 모임에 유저를 찾을 수 없습니다."),
    ALREADY_APPLIED_GROUP(HttpStatus.CONFLICT, "이미 해당 모임에 참여 신청을 보냈습니다."),
    NOT_FOUND_APPLY(HttpStatus.NOT_FOUND, "해당 신청을 찾을 수 없습니다."),

    UNAPPROVED_USER(HttpStatus.LOCKED,"승인되지 않은 사용자입니다."),
    NOT_AUTHORIZED_UPDATE(HttpStatus.LOCKED, "이 게시물을 수정할 권한이 없습니다."),
    NOT_AUTHORIZED_DELETE(HttpStatus.LOCKED, "이 게시물을 삭제할 권한이 없습니다."),

    // MeetsReview
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    INVALID_REVIEW_FOR_MEET(HttpStatus.UNAUTHORIZED, "해당 리뷰가 특정 모임에 유효하지 않다"),

    // Profile
    USER_MISMATCH_ID(HttpStatus.CONFLICT, "회원 번호가 일치하지 않습니다."),

    // InvitedBoard
    NOT_GROUP_CREATOR(HttpStatus.FORBIDDEN, "모임장이 아닙니다."),

    // Likes
    NOT_FOUND_LIKE(HttpStatus.NOT_FOUND, "좋아요를 찾을 수 없습니다??????????"),
    DUPLICATE_LIKE(HttpStatus.LOCKED, "좋아요가 이미 등록되어 있습니다.");


    private final HttpStatus status;

    private final String message;
}
