package com.pallow.pallow.domain.profile.enums;

public enum IDEAL {
    HANDSOME_BEAUTIFUL("이쁘고 잘생긴"),
    STYLISH("스타일리시한"),
    RELIABLE("듬직한"),
    PETITE("아담한"),
    SAME_AGE("또래"),
    OLDER("연상"),
    YOUNGER("연하"),
    CLOSE_DISTANCE("거리가 가까운"),
    SAME_HOBBIES("취미가 같은"),
    COMMUNICATIVE("말이 통하는"),
    SMILEY("잘 웃어주는"),
    NO_SWEARING("욕 안하는"),
    PLEASANT_VOICE("목소리 좋은"),
    GOOD_LISTENER("잘 들어주는"),
    ENERGETIC("활발한"),
    QUIET("조용한"),
    CUTE("애교가 넘치는"),
    MATURE("어른스러운"),
    PASSIONATE("열정적인"),
    CALM("침착한"),
    UNIQUE("개성 넘치는"),
    POLITE("예의 바른"),
    HUMOROUS("유머러스한"),
    PRUDENT("신중한"),
    CONFIDENT("자신감 넘치는"),
    NO_PRETENSE("허세없는"),
    QUIRKY("엉뚱한"),
    INTELLECTUAL("지적인"),
    DILIGENT("성실한"),
    FREE_SPIRITED("자유로운"),
    SENSITIVE("감성적인"),
    LOGICAL("논리적인"),
    IMPULSIVE("즉흥적인"),
    SHY("소심한"),
    COOL("쿨한"),
    TALL("키가 큰"),
    CAPABLE("능력 있는"),
    SMART("똑똑한"),
    CULTURED("교양 있는"),
    CUTE_APPEARANCE("귀여운");

    private String description;

    IDEAL(String description) {
        this.description = description;
    }
}
