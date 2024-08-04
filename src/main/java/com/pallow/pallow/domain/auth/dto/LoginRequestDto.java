package com.pallow.pallow.domain.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String username;
    private String password;
}
//Not blank