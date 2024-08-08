package com.pallow.pallow.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "ID는 공백일 수 없습니다.")
    private String username;

    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    private String email;

    @NotNull(message = "성별을 선택해 주세요.")
    private String gender;

//    private CommonOauth oauth;

}