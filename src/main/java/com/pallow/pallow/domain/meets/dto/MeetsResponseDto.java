package com.pallow.pallow.domain.meets.dto;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.global.enums.CommonStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MeetsResponseDto {

    private Long id;

    private String title;

    private String content;

    private int memberCount;

    private int maxMemberCount;

    private String position;

    private CommonStatus status;

    private String creatorProfilePhoto; // 추가한 필드

    private LocalDateTime createdAt;


    public MeetsResponseDto(Meets meets) {
        this.id = meets.getId();
        this.title = meets.getTitle();
        this.content = meets.getContent();
        this.memberCount = meets.getMemberCount();
        this.maxMemberCount = meets.getMaxMemberCount();
        this.position = meets.getPosition();
        this.status = meets.getStatus();
        this.creatorProfilePhoto = meets.getGroupCreator().getProfile().getPhoto(); // 생성자의 프로필 사진 URL 설정
        this.createdAt = meets.getCreatedAt();
    }
}
