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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
        List<ProfileResponseDto> nearestProfiles = fetchProfiles(userId, first, second, third);

        if (nearestProfiles.size() < 9 && !Objects.equals(third, "")) {
            Set<Long> existingProfileIds = getExistingProfileIds(nearestProfiles);
            nearestProfiles.addAll(fetchProfiles(userId, first, second, null, 9 - nearestProfiles.size(), existingProfileIds));
        }

        if (nearestProfiles.size() < 9) {
            Set<Long> existingProfileIds = getExistingProfileIds(nearestProfiles);
            nearestProfiles.addAll(fetchProfiles(userId, first, null, null, 9 - nearestProfiles.size(), existingProfileIds));
        }

        if (nearestProfiles.size() < 9) {
            Set<Long> existingProfileIds = getExistingProfileIds(nearestProfiles);
            nearestProfiles.addAll(fetchProfiles(userId, null, null, null, 9 - nearestProfiles.size(), existingProfileIds));
        }
        return nearestProfiles;
    }

    private List<ProfileResponseDto> fetchProfiles(Long userId, String first, String second, String third) {
        return fetchProfiles(userId, first, second, third, 9, Set.of());
    }

    private List<ProfileResponseDto> fetchProfiles(Long userId, String first, String second, String third, int limit, Set<Long> existingProfileIds) {
        QProfile profile = QProfile.profile;
        QUser user = QUser.user;
        BooleanExpression positionExpression = matchPosition(profile.position, first, second, third);

        return jpaQueryFactory
                .select(new QProfileResponseDto(profile.id, profile.content, profile.birth,
                        profile.position, profile.mbti, profile.hobby, profile.image, user.name))
                .from(profile)
                .join(profile.user, user)
                .where(profile.user.id.ne(userId).and(positionExpression)
                        .and(profile.position.isNotNull()).and(profile.id.notIn(existingProfileIds)))
                .orderBy(profile.position.asc())
                .limit(limit)
                .fetch();
    }

    private Set<Long> getExistingProfileIds(List<ProfileResponseDto> profiles) {
        return profiles.stream()
                .map(ProfileResponseDto::getId)
                .collect(Collectors.toSet());
    }

    private BooleanExpression matchPosition(StringPath position, String first, String second, String third) {
        if (first == null && second == null && third == null) {
            return null;
        }
        BooleanExpression expression = position.isNotNull();
        if (first != null) {
            expression = expression.and(position.startsWith(first));
        }
        if (second != null) {
            expression = expression.and(position.contains(second));
        }
        if (third != null) {
            expression = expression.and(position.endsWith(third));
        }
        return expression;
    }
}
