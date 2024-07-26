package com.pallow.pallow.domain.auth.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {


    private final String nickname;

    private final String email;

    public AuthResponseDto(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
