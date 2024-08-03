package com.pallow.pallow.domain.profile.repository;

import com.pallow.pallow.domain.profile.entity.Profile;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByUserId(Long id);

    @EntityGraph(attributePaths = {"user"})
    List<Profile> findAllByPosition(String position);
}
