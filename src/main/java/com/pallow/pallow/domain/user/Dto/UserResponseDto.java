package com.pallow.pallow.domain.user.Dto;

import com.pallow.pallow.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final String username;
    private final String email;
    private final String nickname;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
