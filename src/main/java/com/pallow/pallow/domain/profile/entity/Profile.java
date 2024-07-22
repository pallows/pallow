package com.pallow.pallow.domain.profile.entity;

import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.enums.Gender;
import com.pallow.pallow.global.enums.Mbti;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(nullable = false)
    private String photo;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private Mbti mbti;

    @Column(nullable = false)
    private String hobby;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Builder
    public Profile(String content, String birth, Gender gender, Mbti mbti, User createdby,
            String hobby) {
        this.content = content;
        this.birth = birth;
        this.gender = gender;
        this.mbti = mbti;
        this.hobby = hobby;
        this.user = createdby;
    }

    public void update(ProfileRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.birth = requestDto.getBirth();
        this.gender = requestDto.getGender();
        this.mbti = requestDto.getMbti();
        this.hobby = requestDto.getHobby();
    }
}
