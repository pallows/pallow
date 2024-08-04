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
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDto {

    @NotNull(message = "본인 소개가 필요합니다.")
    private String content;
    @NotNull(message = "생일을 입력해 주세요.")
    private String birth;
    @NotNull(message = "지역을 선택해 주세요.")
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


    // 프로필 화면에 띄우기 위함
    private String username;


    public Profile toEntity(User foundUser) {
        return Profile.builder().content(content).birth(birth).position(position).mbti(mbti)
                .createdBy(foundUser).hobby(hobby).photo(photo).build();
    }
}