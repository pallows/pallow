package com.pallow.pallow.domain.meetsreview.repository;

import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<MeetsReview, Long> {

    List<MeetsReview> findAllByMeetsId(Long meetsId);

    Optional<MeetsReview> findByIdAndMeetsId(Long reviewId, Long meetsId);
}
