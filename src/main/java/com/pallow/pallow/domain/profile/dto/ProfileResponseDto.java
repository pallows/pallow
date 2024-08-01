package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.profile.enums.Region;
import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private String content;
    private String birth;
    private Region position;
    private String district;
    private Mbti mbti;
    private String photo;

    public ProfileResponseDto(Profile foundUser) {
        this.content = foundUser.getContent();
        this.birth = foundUser.getBirth();
        this.position = foundUser.getPosition();
        this.district = foundUser.getDistrict();
        this.mbti = foundUser.getMbti();
        this.photo = foundUser.getPhoto();
    }

}
