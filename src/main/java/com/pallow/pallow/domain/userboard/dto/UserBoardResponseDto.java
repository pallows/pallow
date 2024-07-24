package com.pallow.pallow.domain.userboard.dto;

import com.pallow.pallow.domain.userboard.entity.UserBoard;
import lombok.Getter;

@Getter
public class UserBoardResponseDto {

    private long id;
    private String title;
    private String content;
    private String photo;
    private int likesCount;

    public UserBoardResponseDto(UserBoard userBoard) {
        this.id = userBoard.getId();
        this.title = userBoard.getTitle();
        this.content = userBoard.getContent();
        this.photo = userBoard.getPhoto();
        this.likesCount = userBoard.getLikesCount();
    }

}
