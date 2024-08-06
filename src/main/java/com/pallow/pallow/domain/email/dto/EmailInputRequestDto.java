package com.pallow.pallow.domain.email.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailInputRequestDto {
    @Email
    private String email;
}
