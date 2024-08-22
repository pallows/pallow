package com.pallow.pallow.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank(message = "ID는 공백일 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ID는 영문자와 숫자만 포함할 수 있습니다.")
    @Size(min = 4, max = 20, message = "ID는 4자에서 20자 사이여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "비밀번호는 영문자와 숫자만 포함할 수 있습니다.")
    @Size(min = 4, max = 20, message = "비밀번호는 4자에서 20자 사이여야 합니다.")
    private String password;

}
