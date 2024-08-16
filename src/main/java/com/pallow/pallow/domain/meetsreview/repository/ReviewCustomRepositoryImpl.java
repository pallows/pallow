package com.pallow.pallow.domain.meetsreview.repository;

import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import com.pallow.pallow.domain.meetsreview.entity.QMeetsReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QMeetsReview qMeetsReview = QMeetsReview.meetsReview;

    @Override
    @Transactional(readOnly = true)
    public List<MeetsReview> findAllByMeetsId(Long meetsId) {
        return jpaQueryFactory
                .selectFrom(qMeetsReview)
                .where(qMeetsReview.meets.id.eq(meetsId))
                .fetch();
    }
}
