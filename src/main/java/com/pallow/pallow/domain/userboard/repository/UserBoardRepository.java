package com.pallow.pallow.domain.userboard.repository;

import com.pallow.pallow.domain.userboard.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {

}
