package com.pallow.pallow.domain.userboardcomment.repository;

import com.pallow.pallow.domain.userboard.entity.QUserBoard;
import com.pallow.pallow.domain.userboardcomment.entity.QUserBoardComment;
import com.pallow.pallow.domain.userboardcomment.entity.UserBoardComment;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserBoardCommentCustomRepositoryImpl implements UserBoardCommentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    QUserBoardComment qUserBoardComment = QUserBoardComment.userBoardComment;
    QUserBoard qUserBoard = QUserBoard.userBoard;

    @Override
    @Transactional(readOnly = true)
    public UserBoardComment findById(Long id) {
        UserBoardComment userBoardComment = jpaQueryFactory
                .selectFrom(qUserBoardComment)
                .where(qUserBoardComment.id.eq(id))
                .fetchOne();

        if (userBoardComment == null) {
            throw new CustomException(ErrorType.NOT_FOUND_USER_BOARD_COMMENT);
        }

        return userBoardComment;
    }
}
