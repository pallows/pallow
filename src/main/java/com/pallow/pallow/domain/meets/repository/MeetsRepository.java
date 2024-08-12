package com.pallow.pallow.domain.meets.repository;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.global.enums.CommonStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetsRepository extends JpaRepository<Meets, Long> {

    List<Meets> findAllByStatus(CommonStatus status);

    Optional<Meets> findByIdAndStatus(Long meetsId, CommonStatus status);

    @Query(value = "SELECT * FROM meets m WHERE m.position = :position ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Meets> findRandomNearbyMeets(@Param("position") String position, @Param("limit") int limit);

    @Query(value = "SELECT * FROM meets m ORDER BY m.likes_count DESC LIMIT :limit", nativeQuery = true)
    List<Meets> findTopByOrderByLikesCountDesc(@Param("limit") int limit);
}
