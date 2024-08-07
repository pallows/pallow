package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.entity.ProfileItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    public ProfileItem toRequestItem(Profile profile) {

        ProfileItem.ProfileItemBuilder builder = ProfileItem.builder();

        builder.id(profile.getId());
        builder.alcohol(profile.getAlcohol());
        builder.ideal(profile.getIdeal());
        builder.jobs(profile.getJobs());
        builder.pros(profile.getPros());
        builder.mbti(profile.getMbti());
        builder.relationship(profile.getRelationship());
        builder.education(profile.getEducation());
        builder.personality(profile.getPersonality());
        builder.religion(profile.getReligion());
        builder.smoking(profile.getSmoking());
        builder.interest(profile.getInterest());
        builder.position(profile.getPosition());

        return builder.build();

    }


    public ProfileFlaskResponseDto getResponseDto(Profile profile) {

        ProfileFlaskResponseDto.ProfileFlaskResponseDtoBuilder builder = ProfileFlaskResponseDto.builder();

        builder.id(profile.getId());
        builder.name(profile.getUser().getName());
        builder.photo(profile.getPhoto());
        builder.mbti(profile.getMbti());

        return builder.build();
    }
}
