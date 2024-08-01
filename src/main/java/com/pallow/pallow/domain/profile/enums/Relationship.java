package com.pallow.pallow.domain.profile.enums;

public enum Relationship {
    FINDING_FRIEND("내 취향에 맞는 친구 찾는 중"),
    FRIENDSHIP_OR_DATING_OK("친구도 연애도 좋아요"),
    LOOKING_FOR_SERIOUS_RELATIONSHIP("진지한 연애 상대 찾는 중"),
    UNDECIDED("아직 모르겠어요");

    private String description;

    Relationship(String description) {
        this.description = description;
    }
}
