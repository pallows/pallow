package com.pallow.pallow.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Message {

    PROFILE_READ_SUCCESS(HttpStatus.OK, "프로필을 조회하였습니다."),
    PROFILE_CREATE_SUCCESS(HttpStatus.OK, "프로필을 생성하였습니다.");

    private final HttpStatus status;
    private final String message;
}
