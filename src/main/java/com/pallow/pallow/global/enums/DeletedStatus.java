package com.pallow.pallow.global.enums;

import lombok.Getter;

@Getter
public enum DeletedStatus {
    ACTIVE("active"),
    DELETED("deleted");

    private String status;

    DeletedStatus(String status) {
        this.status = status;
    }
}
