package com.pallow.pallow.domain.meets.repository;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.meets.entity.QMeets;
import com.pallow.pallow.global.enums.CommonStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MeetsCustomRepositoryImpl implements MeetsCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QMeets qMeets = QMeets.meets;

    @Override
    @Transactional(readOnly = true)
    public List<Meets> findAllByStatus(CommonStatus status) {
        return jpaQueryFactory
                .selectFrom(qMeets)
                .where(qMeets.status.eq(status))
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Meets> findByIdAndStatus(Long meetsId, CommonStatus status) {
        Meets foundMeets = jpaQueryFactory
                .selectFrom(qMeets)
                .where(qMeets.id.eq(meetsId)
                        .and(qMeets.status.eq(status)))
                .fetchOne();

        return Optional.ofNullable(foundMeets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meets> findTopByOrderByLikesCountDesc(int limit) {
        return jpaQueryFactory
                .selectFrom(qMeets)
                .where(qMeets.status.eq(CommonStatus.ACTIVE)) // assuming status is an enum or string in your entity
                .orderBy(qMeets.likesCount.desc()) // order by likes_count descending
                .limit(limit)
                .fetch();
    }

}
