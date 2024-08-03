package com.pallow.pallow.domain.profile.repository;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.entity.QProfile;
import com.pallow.pallow.domain.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ProfileCustomRepositoryImpl implements ProfileCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QProfile profile = QProfile.profile;

    @Transactional(readOnly = true)
    public List<Profile> findAllByPosition(String position) {
        return jpaQueryFactory
                .selectFrom(profile)
                .leftJoin(profile.user).fetchJoin()
                .where(profile.position.eq(position))
                .fetch();
    }

}
