package com.pallow.pallow.domain.invitedboard.dto;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.enums.InviteStatus;
import lombok.Getter;

@Getter
public class InvitedBoardResponseDto {

    private User user;

    private InviteStatus status;

}
