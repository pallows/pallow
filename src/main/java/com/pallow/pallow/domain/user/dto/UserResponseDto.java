package com.pallow.pallow.domain.user.dto;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.enums.Region;
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
