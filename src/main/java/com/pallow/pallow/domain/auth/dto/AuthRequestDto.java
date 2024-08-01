package com.pallow.pallow.domain.auth.dto;

import com.pallow.pallow.global.common.CommonOauth;
import com.pallow.pallow.global.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @Email
    private String email;

    private CommonOauth oauth;

    @NotBlank
    private String name;

    @NotNull
    private Gender gender;

}
