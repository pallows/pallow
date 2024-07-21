package com.pallow.pallow.global.enums;

public enum Mbti {
    ISTJ("현실주의자"),
    ISTP("장인"),
    INFJ("옹호자"),
    INTJ("전략가"),
    ISFJ("수호자"),
    ISFP("모험가"),
    INFP("중재자"),
    INTP("논리술사"),
    ESTJ("경영자"),
    ESFP("연예인"),
    ENFP("활동가"),
    ENTP("변론가"),
    ESFJ("집정관"),
    ESTP("사업가"),
    ENFJ("선도자"),
    ENTJ("통솔자");

    private String description;

    private Mbti(String description) {
        this.description = description;
    }

}
