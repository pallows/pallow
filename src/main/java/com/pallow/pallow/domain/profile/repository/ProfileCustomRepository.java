package com.pallow.pallow.domain.profile.repository;

import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.entity.Profile;
import java.util.List;

public interface ProfileCustomRepository {

    List<Profile> findAllByPosition(String position);

    List<ProfileResponseDto> findTop9NearestProfiles(Long userId, String first, String second, String third);
}
