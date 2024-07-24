package com.pallow.pallow.global.enums;

import lombok.Getter;

@Getter
public enum ContentType {
    USER_BOARD("userBoard"),
    USER_BOARD_COMMENT("userBoardComment"),
    MEETS_REVIEW("meetsReview");

    private String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }
}
