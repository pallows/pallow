package com.pallow.pallow.domain.user.dto;

import com.pallow.pallow.global.enums.Gender;
import lombok.Getter;

@Getter
public class UserRequestDto {

    private String nickname;
    private String password;
    private String position;
    private String name;
    private Gender gender;
}
