package com.pallow.pallow.domain.meets.dto;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.global.enums.CommonStatus;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MeetsResponseDto {

    private Long id;

    private String title;

    private String content;

    private String image;

    private int memberCount;

    private int maxMemberCount;

    private String position;

    private CommonStatus status;

    private int likesCount;

    private String creatorProfilePhoto; // 추가한 필드

    private LocalDateTime createdAt;

    private String groupCreator;

    private Long groupCreatorId;

    public MeetsResponseDto(Meets meets) {
        this.id = meets.getId();
        this.title = meets.getTitle();
        this.content = meets.getContent();
        this.image = meets.getImage();
        this.memberCount = meets.getMemberCount();
        this.maxMemberCount = meets.getMaxMemberCount();
        this.position = meets.getPosition();
        this.status = meets.getStatus();
        this.likesCount = meets.getLikesCount();
        this.creatorProfilePhoto = meets.getGroupCreator().getProfile().getImage(); // 생성자의 프로필 사진 URL 설정
        this.createdAt = meets.getCreatedAt();
        this.groupCreator = meets.getGroupCreator().getNickname();
        this.groupCreatorId = meets.getGroupCreator().getId()
        ;
    }
}
