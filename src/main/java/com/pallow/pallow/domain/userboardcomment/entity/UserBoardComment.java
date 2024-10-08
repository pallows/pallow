package com.pallow.pallow.domain.userboardcomment.entity;

import com.pallow.pallow.domain.like.entity.Likeable;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.userboard.entity.UserBoard;
import com.pallow.pallow.domain.userboardcomment.dto.UserBoardCommentRequestDto;
import com.pallow.pallow.global.entity.TimeStamp;
import com.pallow.pallow.global.enums.ContentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserBoardComment extends TimeStamp implements Likeable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "boardId", nullable = false)
    private UserBoard userBoard;

    @JoinColumn(nullable = false)
    private String content;

    @Column
    private int likesCount;

    @Builder
    public UserBoardComment(Long id, User user, UserBoard userBoard, String content,
            int likesCount) {
        this.id = id;
        this.user = user;
        this.userBoard = userBoard;
        this.content = content;
        this.likesCount = likesCount;
    }


    public void update(UserBoardCommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    @Override
    public ContentType contentType() {
        return ContentType.USER_BOARD_COMMENT;
    }

    @Override
    public void addLikesCount() {
        this.likesCount++;
    }

    @Override
    public void minusLikesCount() {
        this.likesCount--;
    }
}
