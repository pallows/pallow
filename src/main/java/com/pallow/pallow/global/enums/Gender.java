package com.pallow.pallow.global.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MAN("MAN"),
    WOMAN("WOMAN");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }
}
