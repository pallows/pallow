package com.pallow.pallow.global.dtos;

import com.pallow.pallow.global.enums.Role;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    private String username;
    private String password;
    private String email;
    private String nickname;
    private Role role;
}
