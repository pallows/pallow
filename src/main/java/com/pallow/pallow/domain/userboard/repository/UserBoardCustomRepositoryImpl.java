package com.pallow.pallow.domain.userboard.repository;

import com.pallow.pallow.domain.userboard.entity.QUserBoard;
import com.pallow.pallow.domain.userboard.entity.UserBoard;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Repository
@RequiredArgsConstructor
public class UserBoardCustomRepositoryImpl implements UserBoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QUserBoard qUserBoard = QUserBoard.userBoard;

    @Override
    @Transactional(readOnly = true)
    public UserBoard findById(long id) {
        UserBoard userBoard = jpaQueryFactory
                .selectFrom(qUserBoard)
                .where(qUserBoard.id.eq(id))
                .fetchOne();

        if (userBoard == null) {
            throw new CustomException(ErrorType.NOT_FOUND_BOARD);
        }

        return userBoard;
    }


}
