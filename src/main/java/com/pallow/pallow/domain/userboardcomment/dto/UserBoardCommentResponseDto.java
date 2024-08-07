package com.pallow.pallow.domain.userboardcomment.dto;

import com.pallow.pallow.domain.userboardcomment.entity.UserBoardComment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserBoardCommentResponseDto {

    private long id;
    private String content;
    private int likesCount;
    private String commentUserPhoto;
    private String commentUserNickName;
    private LocalDateTime createdAt;
    private Long commentUserId;

    public UserBoardCommentResponseDto(UserBoardComment userBoardComment) {
        this.id = userBoardComment.getId();
        this.content = userBoardComment.getContent();
        this.likesCount = userBoardComment.getLikesCount();
        this.commentUserPhoto = userBoardComment.getUser().getProfile().getPhoto();
        this.commentUserNickName = userBoardComment.getUser().getNickname();
        this.createdAt = userBoardComment.getCreatedAt();
        this.commentUserId = userBoardComment.getUser().getId();
    }

}
