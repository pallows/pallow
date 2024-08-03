package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.enums.Mbti;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ProfileResponseDto {

    private String content;
    private String birth;
    private String position;
    private Mbti mbti;
    private String hobby;
    private String photo;
    @Setter
    private String name;

    public ProfileResponseDto(Profile foundUser, String name) {
        this.content = foundUser.getContent();
        this.birth = foundUser.getBirth();
        this.position = foundUser.getPosition();
        this.mbti = foundUser.getMbti();
        this.hobby = foundUser.getHobby();
        this.photo = foundUser.getPhoto();
        this.name = name.trim();
    }
}
