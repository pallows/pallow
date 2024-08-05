package com.pallow.pallow.domain.userboard.dto;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.userboard.entity.UserBoard;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserBoardRequestDto {

    @NotNull(message = "제목이 필요합니다.")
    private String title;

    @NotNull(message = "설명이 필요합니다.")
    private String content;

    @NotNull(message = "사진이 필요합니다.")
    private MultipartFile image;

    public UserBoard toEntity(User createdBy, String imageUrl) {
        return UserBoard.builder().title(title).content(content).image(imageUrl).user(createdBy).build();
    }
}
