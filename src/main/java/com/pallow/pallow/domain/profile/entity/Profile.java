package com.pallow.pallow.domain.profile.entity;

import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.enums.Alcohol;
import com.pallow.pallow.domain.profile.enums.Education;
import com.pallow.pallow.domain.profile.enums.IDEAL;
import com.pallow.pallow.domain.profile.enums.Interest;
import com.pallow.pallow.domain.profile.enums.Jobs;
import com.pallow.pallow.domain.profile.enums.Personality;
import com.pallow.pallow.domain.profile.enums.Pros;
import com.pallow.pallow.domain.profile.enums.Relationship;
import com.pallow.pallow.domain.profile.enums.Religion;
import com.pallow.pallow.domain.profile.enums.Smoking;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.profile.enums.Region;
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

    @Column
    private Interest interest;

    @Column
    private Alcohol alcohol;

    @Column
    private Education education;

    @Column
    private IDEAL ideal;

    @Column
    private Jobs jobs;

    @Column
    private Personality personality;

    @Column
    private Pros pros;

    @Column
    private Relationship relationship;

    @Column
    private Religion religion;

    @Column
    private Smoking smoking;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Builder
    public Profile(String content, String birth, Region position, Mbti mbti, User createdBy,
            Interest interest, String photo) {
        this.content = content;
        this.birth = birth;
        this.mbti = mbti;
        this.position = position;
        this.photo = photo;
        this.user = createdBy;
    }

    public void update(ProfileRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.birth = requestDto.getBirth();
        this.position = requestDto.getPosition();
        this.mbti = requestDto.getMbti();
    }
}
