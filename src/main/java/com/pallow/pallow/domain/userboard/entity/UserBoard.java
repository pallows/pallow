package com.pallow.pallow.domain.userboard.entity;

import com.pallow.pallow.domain.like.entity.Likeable;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.userboard.dto.UserBoardRequestDto;
import com.pallow.pallow.domain.userboardcomment.entity.UserBoardComment;
import com.pallow.pallow.global.entity.TimeStamp;
import com.pallow.pallow.global.enums.ContentType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserBoard extends TimeStamp implements Likeable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String image;

    @Column
    private int likesCount;

    @OneToMany(mappedBy = "userBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBoardComment> comments = new ArrayList<>();

    @Builder
    public UserBoard(Long id, User user, String title, String content, String image,
                     int likesCount) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.image = image;
        this.likesCount = likesCount;
    }


    public void update(UserBoardRequestDto requestDto, String imageUrl) {
        this.content = requestDto.getContent();
        this.image = imageUrl;
        this.title = requestDto.getTitle();
    }

    @Override
    public ContentType contentType() {
        return ContentType.USER_BOARD;
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
