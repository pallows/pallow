package com.pallow.pallow.domain.user.repository;

import com.pallow.pallow.domain.user.entity.QUser;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QUser qUser = QUser.user;

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        User foundUser = jpaQueryFactory
                .selectFrom(qUser)
                .where(qUser.id.eq(id))
                .fetchOne();

        if (foundUser == null) {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }

        return foundUser;
    }

}
