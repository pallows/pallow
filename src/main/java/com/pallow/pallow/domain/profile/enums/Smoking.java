package com.pallow.pallow.domain.profile.enums;

public enum Smoking {
    NONE("비흡연"),
    QUITTING("금연 중"),
    ELECTRIC("전자 담배"),
    CIGAR("흡연 중");

    private String description;

    Smoking(String description) {
        this.description = description;
    }
}
