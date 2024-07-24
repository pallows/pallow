package com.pallow.pallow.domain.userboard.entity;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.userboard.dto.UserBoardRequestDto;
import com.pallow.pallow.global.entity.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class UserBoard extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String photo;

    @Column(nullable = false)
    private int likesCount;

    @Builder
    public UserBoard(Long id, User user, String title, String content, String photo,
            int likesCount) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.photo = photo;
        this.likesCount = likesCount;
    }


    public void update(UserBoardRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.photo = requestDto.getPhoto();
        this.title = requestDto.getTitle();
    }
}
