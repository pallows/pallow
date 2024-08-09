package com.pallow.pallow.domain.profile.entity;

import com.pallow.pallow.domain.profile.enums.Alcohol;
import com.pallow.pallow.domain.profile.enums.Education;
import com.pallow.pallow.domain.profile.enums.Jobs;
import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.profile.enums.Personality;
import com.pallow.pallow.domain.profile.enums.Pros;
import com.pallow.pallow.domain.profile.enums.Relationship;
import com.pallow.pallow.domain.profile.enums.Religion;
import com.pallow.pallow.domain.profile.enums.Smoking;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileItem {

    private Long id;
    private Alcohol alcohol;
    private Education education;
    private String ideal;
    private String interest;
    private Jobs jobs;
    private Mbti mbti;
    private Personality personality;
    private Pros pros;
    private String region;
    private Relationship relationship;
    private Religion religion;
    private Smoking smoking;

}
