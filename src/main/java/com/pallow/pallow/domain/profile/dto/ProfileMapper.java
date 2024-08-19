package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.entity.ProfileItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    public ProfileItem toRequestItem(Profile profile) {

        return ProfileItem.builder()
                .id(profile.getId())
                .alcohol(profile.getAlcohol())
                .ideal(profile.getIdeal())
                .jobs(profile.getJobs())
                .pros(profile.getPros())
                .mbti(profile.getMbti())
                .relationship(profile.getRelationship())
                .education(profile.getEducation())
                .personality(profile.getPersonality())
                .religion(profile.getReligion())
                .smoking(profile.getSmoking())
                .interest(profile.getInterest())
                .region(profile.getPosition())
                .build();
    }


    public ProfileFlaskResponseDto getResponseDto(Profile profile) {

        return ProfileFlaskResponseDto.builder()
                .id(profile.getId())
                .name(profile.getUser().getName())
                .image(profile.getImage())
                .mbti(profile.getMbti())
                .nickname(profile.getUser().getNickname())
                .build();
    }
}
