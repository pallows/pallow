package com.pallow.pallow.domain.profile.enums;

public enum Jobs {
    STUDENT("학생"),
    PART_TIME("알바"),
    FREE_LANCER("프리랜서"),
    OFFICE("회사원"),
    SELF("자영업"),
    PRO("전문직"),
    MEDICAL("의료직"),
    EDUCATION("교육직"),
    FINANCE("금융직"),
    RESEARCH("연구, 기술직"),
    PUBLIC("공무원"),
    BUSINESS("사업가"),
    SOLDIER("군인"),
    PREPARE("취업 준비중"),
    ARTIST("예술가"),
    ATHLETIC("체육인"),
    MUSICIAN("음악가"),
    INFLUENCER("인플루언서"),
    YOUTUBER("유튜버"),
    REST("쉬고 있어요"),
    ETC("기타");

    private String description;

    Jobs(String description) {
        this.description = description;
    }
}
