package com.pallow.pallow.global.enums;

import lombok.Getter;

@Getter
public enum CommonStatus {
    ACTIVE("active"),
    DELETED("deleted");

    private String status;

    CommonStatus(String status) {
        this.status = status;
    }
}
