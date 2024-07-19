package com.pallow.pallow.global.enums;

import lombok.Getter;

@Getter
public enum InviteStatus {
    WAITING("waiting"),
    ACCEPTED("accepted"),
    DECLINED("declined");

    private String status;

    InviteStatus(String inviteStatus) {
        this.status = inviteStatus;
    }
}
