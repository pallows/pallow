package com.pallow.pallow.domain.profile.enums;

public enum Religion {
    None("무교"),
    Christian("기독교"),
    Catholic("천주교"),
    Buddhism("불교"),
    WonBuddhism("원불교"),
    ETC("기타 종교");

    private String description;

    Religion(String description) {
        this.description = description;
    }
}
