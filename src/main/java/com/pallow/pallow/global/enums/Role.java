package com.pallow.pallow.global.enums;

import lombok.Getter;

@Getter
public enum Role {
    ROOT("ADMINISTER"),
    USER("USER");

    private String role;

    Role(String role) {
        this.role = role;
    }
}
