package com.pallow.pallow.domain.meetsreview.repository;

import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import java.util.List;

public interface ReviewCustomRepository {

    List<MeetsReview> findAllByMeetsId(Long meetsId);

}
