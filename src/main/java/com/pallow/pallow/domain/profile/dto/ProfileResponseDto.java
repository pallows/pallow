package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.enums.Alcohol;
import com.pallow.pallow.domain.profile.enums.Education;
import com.pallow.pallow.domain.profile.enums.Jobs;
import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.profile.enums.Personality;
import com.pallow.pallow.domain.profile.enums.Pros;
import com.pallow.pallow.domain.profile.enums.Relationship;
import com.pallow.pallow.domain.profile.enums.Religion;
import com.pallow.pallow.domain.profile.enums.Smoking;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ProfileResponseDto {

    private Long id;
    private String content;
    private String birth;
    private String position;
    private Mbti mbti;
    private String hobby;
    private String image;

    private String interest;
    private Alcohol alcohol;
    private Education education;
    private String ideal;
    private Jobs jobs;
    private Personality personality;
    private Relationship relationship;
    private Religion religion;
    private Smoking smoking;
    private Pros pros;

    @Setter
    private String name;

    @QueryProjection
    public ProfileResponseDto(Long id, String content, String birth, String position, Mbti mbti, String hobby, String image, String name) {
        this.content = content;
        this.birth = birth;
        this.position = position;
        this.mbti = mbti;
        this.hobby = hobby;
        this.image = image;
        this.name = name.trim();
        this.id = id;
    }

    public ProfileResponseDto(Profile foundUser, String name) {
        this.id = foundUser.getId();
        this.content = foundUser.getContent();
        this.birth = foundUser.getBirth();
        this.position = foundUser.getPosition();
        this.mbti = foundUser.getMbti();
        this.image = foundUser.getImage();
        this.hobby = foundUser.getHobby();
        this.name = name.trim();
    }
}
