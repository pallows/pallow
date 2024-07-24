package com.pallow.pallow.domain.invitedboard.repository;

import com.pallow.pallow.domain.invitedboard.dto.InvitedBoardResponseDto;
import com.pallow.pallow.domain.invitedboard.entity.InvitedBoard;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.enums.InviteStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitedBoardRepository extends JpaRepository<InvitedBoard, Long> {

    boolean existsByUserAndMeets(User user, Meets meets);

    Optional<InvitedBoard> findByUserAndMeetsAndStatus(User user, Meets meets, InviteStatus status);

    List<InvitedBoardResponseDto> findAllByStatus(User user, InviteStatus inviteStatus);
}
