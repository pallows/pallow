package com.pallow.pallow.domain.invitedboard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class InvitedBoardRequestDto {

    @NotBlank
    private Long userId;

}
