package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.enums.Alcohol;
import com.pallow.pallow.domain.profile.enums.Education;
import com.pallow.pallow.domain.profile.enums.IDEAL;
import com.pallow.pallow.domain.profile.enums.Jobs;
import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.profile.enums.Personality;
import com.pallow.pallow.domain.profile.enums.Pros;
import com.pallow.pallow.domain.profile.enums.Relationship;
import com.pallow.pallow.domain.profile.enums.Religion;
import com.pallow.pallow.domain.profile.enums.Smoking;
import com.pallow.pallow.domain.user.entity.User;
import com.querydsl.core.annotations.QueryProjection;
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

    private Alcohol alcohol;
    private Education education;
    private IDEAL ideal;
    private Jobs jobs;
    private Personality personality;
    private Relationship relationship;
    private Religion religion;
    private Smoking smoking;
    private Pros pros;

    @Setter
    private String name;

    @QueryProjection
    public ProfileResponseDto(String content, String birth, String position, Mbti mbti, String hobby, String photo, String name) {
        this.content = content;
        this.birth = birth;
        this.position = position;
        this.mbti = mbti;
        this.hobby = hobby;
        this.photo = photo;
        this.name = name.trim();
    }

    public ProfileResponseDto(Profile foundUser, String name) {
        this.content = foundUser.getContent();
        this.birth = foundUser.getBirth();
        this.position = foundUser.getPosition();
        this.mbti = foundUser.getMbti();
        this.hobby = foundUser.getHobby();
        this.photo = foundUser.getPhoto();
        this.name = name.trim();
    }

    public Profile toEntity(User user) {
        return Profile.builder().content(content).birth(birth).position(position)
                .mbti(mbti).createdBy(user).hobby(hobby).photo(photo).build();
    }
}
