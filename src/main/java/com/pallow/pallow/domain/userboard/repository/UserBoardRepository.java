package com.pallow.pallow.domain.userboard.repository;

import com.pallow.pallow.domain.userboard.entity.UserBoard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {

    List<UserBoard> findAllById(long userId);
}
