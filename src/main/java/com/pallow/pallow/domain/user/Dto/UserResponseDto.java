package com.pallow.pallow.domain.user.Dto;

import com.pallow.pallow.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final String username;
    private final String email;
    private final String nickname;
    private final String position;
    // private String Position

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.nickname = user.getEmail();
        this.position = user.getPosition();
    }
}
