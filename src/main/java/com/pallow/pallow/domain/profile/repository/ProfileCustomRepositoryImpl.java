package com.pallow.pallow.domain.profile.repository;

import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.dto.QProfileResponseDto;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.entity.QProfile;
import com.pallow.pallow.domain.user.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ProfileCustomRepositoryImpl implements ProfileCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Profile> findAllByPosition(String position) {
        QProfile profile = QProfile.profile;
        return jpaQueryFactory
                .selectFrom(profile)
                .leftJoin(profile.user).fetchJoin()
                .where(profile.position.eq(position))
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfileResponseDto> findTop9NearestProfiles(Long userId, String first, String second, String third) {
        QProfile profile = QProfile.profile;
        QUser user = QUser.user;

        List<ProfileResponseDto> nearestProfiles = jpaQueryFactory
                .select(new QProfileResponseDto(
                        profile.content,
                        profile.birth,
                        profile.position,
                        profile.mbti,
                        profile.hobby,
                        profile.photo,
                        user.name
                ))
                .from(profile)
                .join(profile.user, user)
                .where(profile.user.id.ne(userId)
                        .and(matchPosition(profile.position, first, second, third)))
                .orderBy(profile.position.asc())
                .limit(9)
                .fetch();

        if (nearestProfiles.size() < 9) {
            List<ProfileResponseDto> additionalProfiles = jpaQueryFactory
                    .select(new QProfileResponseDto(
                            profile.content,
                            profile.birth,
                            profile.position,
                            profile.mbti,
                            profile.hobby,
                            profile.photo,
                            user.name
                    ))
                    .from(profile)
                    .join(profile.user, user)
                    .where(profile.user.id.ne(userId)
                            .and(profile.position.isNotNull()))
                    .orderBy(profile.position.asc())
                    .limit(9 - nearestProfiles.size())
                    .fetch();

            nearestProfiles.addAll(additionalProfiles);
        }
        return nearestProfiles;
    }

    private BooleanExpression matchPosition(StringPath position, String first, String second, String third) {
        return position.startsWith(first)
                .and(position.contains(second))
                .and(position.endsWith(third));
    }
}
