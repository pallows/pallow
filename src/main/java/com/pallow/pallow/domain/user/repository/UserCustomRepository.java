package com.pallow.pallow.domain.user.repository;

import com.pallow.pallow.domain.user.entity.User;

public interface UserCustomRepository {

    User findById(Long id);

}
