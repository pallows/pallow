package com.pallow.pallow.domain.userboardcomment.repository;

import com.pallow.pallow.domain.userboard.entity.UserBoard;
import com.pallow.pallow.domain.userboardcomment.entity.UserBoardComment;

public interface UserBoardCommentCustomRepository {

    UserBoardComment findById(Long id);

}
