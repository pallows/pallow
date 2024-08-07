package com.pallow.pallow.domain.profile.enums;

public enum Religion {
    NONE("무교"),
    CHRISTIAN("기독교"),
    CATHOLIC("천주교"),
    BUDDHISM("불교"),
    WONBUDDHISM("원불교"),
    ETC("기타 종교");
    private String description;
    Religion(String description) {
        this.description = description;
    }
}
