package com.pallow.pallow.domain.userboard.dto;

import com.pallow.pallow.domain.userboard.entity.UserBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserBoardResponseDto {

    private long id;
    private String title;
    private String content;
    private String image;
    private int likesCount;
    private String userNickName;
    private String userProfileImage;
    private LocalDateTime createdAt;
    private Long boardUserId;

    public UserBoardResponseDto(UserBoard userBoard) {
        this.id = userBoard.getId();
        this.title = userBoard.getTitle();
        this.content = userBoard.getContent();
        this.image = userBoard.getImage();
        this.likesCount = userBoard.getLikesCount();
        this.userNickName = userBoard.getUser().getNickname();
        this.userProfileImage = userBoard.getUser().getProfile().getImage();
        this.createdAt = userBoard.getCreatedAt();
        this.boardUserId = userBoard.getUser().getId();
    }

}

