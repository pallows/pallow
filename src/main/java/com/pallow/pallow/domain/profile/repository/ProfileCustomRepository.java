package com.pallow.pallow.domain.profile.repository;

import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.global.enums.CommonStatus;
import java.util.List;
import java.util.Optional;

public interface ProfileCustomRepository {

    Optional<Profile> findByUserId(Long userId);

    List<Profile> findAllByPositionAndUserStatus(String position, CommonStatus status);

    ProfileResponseDto findByProfileId(Long id);

    List<ProfileResponseDto> findTop9NearestProfiles(Long userId, String first, String second,
            String third);
}
