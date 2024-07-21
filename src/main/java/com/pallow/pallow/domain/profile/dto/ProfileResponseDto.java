package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.global.enums.Gender;
import com.pallow.pallow.global.enums.Mbti;
import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private Long id;
    private String content;
    private String birth;
    private Gender gender;
    private Mbti mbti;
    private String hobby;
    private String photo;

    public ProfileResponseDto(Profile foundUser) {
        this.id = foundUser.getId();
        this.content = foundUser.getContent();
        this.birth = foundUser.getBirth();
        this.gender = foundUser.getGender();
        this.mbti = foundUser.getMbti();
        this.hobby = foundUser.getHobby();
        this.photo = foundUser.getPhoto();
    }

}
