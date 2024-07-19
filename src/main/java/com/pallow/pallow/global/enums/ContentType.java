package com.pallow.pallow.global.enums;

import lombok.Getter;

@Getter
public enum ContentType {
    USER_BOARD("userBoard"),
    USER_BOARD_COMMENT("userBoardComment"),
    GROUP_BOARD("groupBoard"),
    GROUP_BOARD_COMMENT("groupBoardComment");

    private String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }
}
