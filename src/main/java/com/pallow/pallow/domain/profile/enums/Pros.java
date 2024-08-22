package com.pallow.pallow.domain.profile.enums;

public enum Pros {
    FASHION("패션 센스"),
    EXPRESS("표현을 잘해요"),
    EMPATHY("공감 능력"),
    FOREIGN("외국어"),
    CAREER("뛰어난 커리어"),
    MONEY("높은 경제력"),
    DETAILED("섬세하다"),
    BRAIN("섹시한 두뇌");

    private String description;

    Pros (String description) {
        this.description = description;
    }
}
