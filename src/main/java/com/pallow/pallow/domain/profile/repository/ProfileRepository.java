package com.pallow.pallow.domain.profile.repository;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.global.enums.CommonStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserId(Long id);

    List<Profile> findAllByPositionAndUserStatus(String position, CommonStatus status);

}
