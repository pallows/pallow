package com.pallow.pallow.domain.meetsreview.repository;

import com.pallow.pallow.domain.meets.dto.MeetsResponseDto;
import com.pallow.pallow.domain.meets.entity.QMeets;
import com.pallow.pallow.domain.meetsreview.dto.ReviewResponseDto;
import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import com.pallow.pallow.domain.meetsreview.entity.QMeetsReview;
import com.pallow.pallow.domain.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QMeetsReview meetsReview = QMeetsReview.meetsReview;
    QUser user = QUser.user;
    QMeets meets = QMeets.meets;

    /**
     * 현재 : Entity 조회
     * 최적화할 것 : Dto 조회
     * @param meetsId
     * @return
     */
    @Override
    public List<MeetsReview> findAllByMeetsId(Long meetsId) {
        List<Long> ids = jpaQueryFactory
                .select(meetsReview.id)
                .from(meetsReview)
                .where(meetsReview.meets.id.eq(meetsId))
                .fetch();

        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        return jpaQueryFactory
                .select(Projections.constructor(MeetsReview.class,
                        meetsReview.id,
                        meetsReview.content,
                        meetsReview.likesCount,
                        meetsReview.user.id,
                        meetsReview.meets.id,
                        user.username))
                .from(meetsReview)
                .join(user).on(meetsReview.user.id.eq(user.id))
                .join(meets).on(meetsReview.meets.id.eq(meets.id))
                .where(meetsReview.id.in(ids))
                .fetch();


    }

}
