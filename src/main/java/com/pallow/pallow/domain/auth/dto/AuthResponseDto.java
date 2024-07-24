package com.pallow.pallow.domain.auth.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {

    private final Long userId;

    private final String nickname;

    private final String email;

    public AuthResponseDto(Long userId, String nickname, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
    }
}
