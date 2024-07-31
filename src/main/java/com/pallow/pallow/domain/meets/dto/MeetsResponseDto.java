package com.pallow.pallow.domain.meets.dto;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.global.enums.CommonStatus;
import lombok.Getter;

@Getter
public class MeetsResponseDto {

    private Long id;

    private String title;

    private String content;

    private long memberCount;

    private String position;

    private CommonStatus status;

    public MeetsResponseDto(Meets meets) {
        this.id = meets.getId();
        this.title = meets.getTitle();
        this.content = meets.getContent();
        this.memberCount = meets.getMemberCount();
        this.position = meets.getPosition();
        this.status = meets.getStatus();
    }
}
