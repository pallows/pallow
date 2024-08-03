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
        builder.region(profile.getPosition());

        return builder.build();

    }


    public ProfileFlaskReseponseDto getResponseDto(Profile profile) {

        ProfileFlaskReseponseDto.ProfileFlaskReseponseDtoBuilder builder = ProfileFlaskReseponseDto.builder();

        builder.id(profile.getId());
        builder.photo(profile.getPhoto());
        builder.birth(profile.getBirth());
        builder.position(profile.getPosition());
        builder.mbti(profile.getMbti());

        return builder.build();
    }
}
