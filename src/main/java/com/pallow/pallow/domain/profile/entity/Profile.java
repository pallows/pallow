package com.pallow.pallow.domain.profile.entity;

import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.enums.Mbti;
import com.pallow.pallow.global.enums.Region;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long Id;

    @Column
    private String photo;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String birth;

    @Column
    @Enumerated(EnumType.STRING)
    private Region position;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Mbti mbti;

    @Column(nullable = false)
    private String hobby;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Builder
    public Profile(String content, String birth, Region position, Mbti mbti, User createdBy,
            String hobby, String photo) {
        this.content = content;
        this.birth = birth;
        this.mbti = mbti;
        this.position = position;
        this.hobby = hobby;
        this.photo = photo;
        this.user = createdBy;
    }

    public void update(ProfileRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.birth = requestDto.getBirth();
        this.position = requestDto.getPosition();
        this.mbti = requestDto.getMbti();
        this.hobby = requestDto.getHobby();
    }
}
