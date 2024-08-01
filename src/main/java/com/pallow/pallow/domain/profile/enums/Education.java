package com.pallow.pallow.domain.profile.enums;

public enum Education {
    HIGH("고졸"),
    UNIV("대졸"),
    UNIV_ON("대학교 재학"),
    GRADUATE_ON("대학원 재학"),
    ETC("기타");

    private String value;

    Education(String value) {
        this.value = value;
    }
}
