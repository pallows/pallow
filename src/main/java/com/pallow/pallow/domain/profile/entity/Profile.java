package com.pallow.pallow.domain.profile.entity;

import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.enums.Alcohol;
import com.pallow.pallow.domain.profile.enums.Education;
import com.pallow.pallow.domain.profile.enums.Jobs;
import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.profile.enums.Personality;
import com.pallow.pallow.domain.profile.enums.Pros;
import com.pallow.pallow.domain.profile.enums.Relationship;
import com.pallow.pallow.domain.profile.enums.Religion;
import com.pallow.pallow.domain.profile.enums.Smoking;
import com.pallow.pallow.domain.user.entity.User;
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
    private Long id;

    @Column
    private String image;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String birth;

    @Column
    private String position;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Mbti mbti;

    @Column
    private String interest;

    @Column
    @Enumerated(EnumType.STRING)
    private Alcohol alcohol;

    @Column
    @Enumerated(EnumType.STRING)
    private Education education;

    @Column
    private String ideal;

    @Column
    @Enumerated(EnumType.STRING)
    private Jobs jobs;

    @Column
    @Enumerated(EnumType.STRING)
    private Personality personality;

    @Column
    @Enumerated(EnumType.STRING)
    private Pros pros;

    @Column
    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @Column
    @Enumerated(EnumType.STRING)
    private Religion religion;

    @Column
    @Enumerated(EnumType.STRING)
    private Smoking smoking;

    @Column
    private String hobby;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public Profile(String content, String birth, String position, Mbti mbti, User createdBy,
            String interest, String image, String hobby, Alcohol alcohol, Education education,
            String ideal, Jobs jobs, Personality personality, Pros pros,
            Relationship relationship, Religion religion, Smoking smoking) {
        this.content = content;
        this.birth = birth;
        this.mbti = mbti;
        this.position = position;
        this.image = image;
        this.user = createdBy;
        this.hobby = hobby;
        this.interest = interest;
        this.alcohol = alcohol;
        this.education = education;
        this.ideal = ideal;
        this.jobs = jobs;
        this.personality = personality;
        this.relationship = relationship;
        this.religion = religion;
        this.smoking = smoking;
        this.pros = pros;
    }

    public void update(ProfileRequestDto requestDto, String imageUrl) {
        this.content = requestDto.getContent();
        this.position = requestDto.getPosition();
        this.mbti = requestDto.getMbti();
        this.hobby = requestDto.getHobby();
        this.image = imageUrl;
        this.smoking = requestDto.getSmoking();
    }
}
