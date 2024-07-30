package com.pallow.pallow.domain.user.Dto;

import com.pallow.pallow.global.enums.Gender;
import lombok.Getter;

@Getter
public class UserRequestDto {
    private String nickname;
    private String password;
    private String position;
    private Gender gender;
}
