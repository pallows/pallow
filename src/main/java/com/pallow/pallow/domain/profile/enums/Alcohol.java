package com.pallow.pallow.domain.profile.enums;

public enum Alcohol {
    NOPE("술은 안 마셔요"),
    SOMETIMES("술은 가끔 마셔요"),
    ALWAYS("술은 내 친구");

    private String description;

    Alcohol(String description) {
        this.description = description;
    }
}
