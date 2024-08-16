package com.pallow.pallow.domain.meets.repository;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.global.enums.CommonStatus;
import java.util.List;
import java.util.Optional;

public interface MeetsCustomRepository {

    List<Meets> findAllByStatus(CommonStatus status);

    Optional<Meets> findByIdAndStatus(Long meetsId, CommonStatus status);

}
