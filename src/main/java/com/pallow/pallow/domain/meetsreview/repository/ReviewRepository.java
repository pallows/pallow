package com.pallow.pallow.domain.meetsreview.repository;

import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<MeetsReview, Long>, ReviewCustomRepository {

}