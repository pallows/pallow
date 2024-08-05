package com.pallow.pallow.domain.userboard.dto;

import com.pallow.pallow.domain.userboard.entity.UserBoard;
import lombok.Getter;

@Getter
public class UserBoardResponseDto {

    private long id;
    private String title;
    private String content;
    private String image;
    private int likesCount;

    public UserBoardResponseDto(UserBoard userBoard) {
        this.id = userBoard.getId();
        this.title = userBoard.getTitle();
        this.content = userBoard.getContent();
        this.image = userBoard.getImage();
        this.likesCount = userBoard.getLikesCount();
    }

}
