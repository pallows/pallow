package com.pallow.pallow.domain.userboardcomment.dto;

import com.pallow.pallow.domain.userboardcomment.entity.UserBoardComment;
import lombok.Getter;

@Getter
public class UserBoardCommentResponseDto {

    private long id;
    private String content;
    private int likesCount;

    public UserBoardCommentResponseDto(UserBoardComment userBoardComment) {
        this.id = userBoardComment.getId();
        this.content = userBoardComment.getContent();
        this.likesCount = userBoardComment.getLikesCount();
    }

}
