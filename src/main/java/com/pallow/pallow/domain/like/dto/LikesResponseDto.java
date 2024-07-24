package com.pallow.pallow.domain.like.dto;

import com.pallow.pallow.domain.like.entity.Likes;
import com.pallow.pallow.global.enums.ContentType;
import lombok.Getter;

@Getter
public class LikesResponseDto {

    private Long id;
    private ContentType contentType;
    private Long contentId;

    public LikesResponseDto(Likes likes) {
        this.id = likes.getId();
        this.contentType = likes.getContentType();
        this.contentId = likes.getContentId();
    }
}
