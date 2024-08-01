package com.pallow.pallow.global.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public static Gender fromString(String gender) {
        for (Gender g : Gender.values()) {
            if (g.gender.equalsIgnoreCase(gender)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Unknown gender: " + gender);
    }
}