package com.pallow.pallow.domain.userboardcomment.dto;

import com.pallow.pallow.domain.userboardcomment.entity.UserBoardComment;
import java.time.LocalDateTime;
import lombok.Getter;

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
        this.commentUserPhoto = userBoardComment.getUser().getProfile().getImage();
        this.commentUserNickName = userBoardComment.getUser().getNickname();
        this.createdAt = userBoardComment.getCreatedAt();
        this.commentUserId = userBoardComment.getUser().getId();
    }

}
