package com.pallow.pallow.domain.invitedboard.dto;

import com.pallow.pallow.domain.invitedboard.entity.InvitedBoard;
import com.pallow.pallow.global.enums.InviteStatus;
import lombok.Getter;

@Getter
public class InvitedBoardResponseDto {

    private String nickname;

    private InviteStatus status;

    public InvitedBoardResponseDto(InvitedBoard invitedBoard) {
        this.nickname = invitedBoard.getUser().getNickname();
        this.status = invitedBoard.getStatus();
    }
}
