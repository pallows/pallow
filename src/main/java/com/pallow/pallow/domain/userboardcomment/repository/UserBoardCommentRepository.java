package com.pallow.pallow.domain.userboardcomment.repository;

import com.pallow.pallow.domain.userboardcomment.entity.UserBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBoardCommentRepository extends JpaRepository<UserBoardComment, Long> {

}
