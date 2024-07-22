package com.pallow.pallow.domain.invitedboard.repository;

import com.pallow.pallow.domain.invitedboard.entity.InvitedBoard;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitedBoardRepository extends JpaRepository<InvitedBoard, Long> {

    boolean existsByUserAndMeets(User user, Meets meets);
}
