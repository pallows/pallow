package com.pallow.pallow.domain.userboard.repository;

import com.pallow.pallow.domain.userboard.entity.UserBoard;

public interface UserBoardCustomRepository {

    UserBoard findById(long id);

}
