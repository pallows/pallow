package com.pallow.pallow.domain.profile.enums;

public enum Personality {
    CALM("차분한"),
    KIND("상냥한"),
    POSITIVE("긍정적인"),
    CREATIVE("창의적인"),
    OPTIMISTIC("낙천적인"),
    CARING("자상한"),
    PRIM("꼼꼼한"),
    CAREFUL("신중한"),
    HUMOR("유머러스한"),
    HOT("화끈한"),
    UPSTAGE("도도한"),
    Enthusiastic("열정적인"),
    BOLD("대범한"),
    CUTE("귀여운"),
    SMOOTH("부드러운"),
    SENTIMENTAL("감성적인"),
    SOCIABLE("사교적인"),
    FAITHFUL("성실한"),
    LOGICAL("논리적인");

    private String description;

    Personality(String description) {
        this.description = description;
    }


}
