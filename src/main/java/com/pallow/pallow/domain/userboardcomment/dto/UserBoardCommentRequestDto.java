package com.pallow.pallow.domain.userboardcomment.dto;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.userboard.entity.UserBoard;
import com.pallow.pallow.domain.userboardcomment.entity.UserBoardComment;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserBoardCommentRequestDto {

    @NotNull(message = "내용이 필요합니다.")
    private String content;

    public UserBoardComment toEntity(User createdBy) {
        return UserBoardComment.builder().content(content).build();
    }

}
