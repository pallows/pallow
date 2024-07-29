package com.pallow.pallow.domain.invitedboard.service;

import com.pallow.pallow.domain.invitedboard.dto.InvitedBoardRequestDto;
import com.pallow.pallow.domain.invitedboard.dto.InvitedBoardResponseDto;
import com.pallow.pallow.domain.invitedboard.entity.InvitedBoard;
import com.pallow.pallow.domain.invitedboard.repository.InvitedBoardRepository;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.meets.repository.MeetsRepository;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.InviteStatus;
import com.pallow.pallow.global.exception.CustomException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvitedBoardService {


    private final InvitedBoardRepository invitedBoardRepository;
    private final UserRepository userRepository;
    private final MeetsRepository meetsRepository;

    public void applyForGroup(long groupId, InvitedBoardRequestDto requestDto, User user) {
        User wantToApplyUser = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));

        Meets meet = meetsRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_GROUP));

        if (isAppliedUser(wantToApplyUser, meet)) {
            throw new CustomException(ErrorType.ALREADY_APPLIED_GROUP);
        }

        applyUserToGroup(user, meet);
    }

    private boolean isAppliedUser(User user, Meets meets) {
        return userRepository.existsById(user.getId())
                && invitedBoardRepository.existsByUserAndMeets(user, meets);
    }

    private void applyUserToGroup(User user, Meets meets) {
        InvitedBoard invitedBoard = InvitedBoard.builder().user(user).meets(meets).build();
        invitedBoardRepository.save(invitedBoard);
    }

    @Transactional
    public void acceptApply(long groupId, long invitationId, User user) {
        InvitedBoard invitedBoard = invitedBoardRepository.findById(invitationId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_APPLY));
        if (!isUserGroupCreator(groupId, user)) {
            throw new CustomException(ErrorType.NOT_GROUP_CREATOR);
        }
        invitedBoard.acceptInvite();
    }

    @Transactional
    public void declineApply(long groupId, long invitationId, User user) {
        InvitedBoard invitedBoard = invitedBoardRepository.findById(invitationId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_APPLY));
        if (!isUserGroupCreator(groupId, user)) {
            throw new CustomException(ErrorType.NOT_GROUP_CREATOR);
        }
        invitedBoard.rejectInvite();
    }

    // 유저가 그룹에 포함되있는지 검사
    public boolean isUserInGroup(User user, Meets meets) {
        Optional<InvitedBoard> invitedBoard = invitedBoardRepository.findByUserAndMeetsAndStatus(
                user, meets, InviteStatus.ACCEPTED);
        return invitedBoard.isPresent();
    }

    public List<InvitedBoardResponseDto> getAllInvitation(long groupId, User user) {
        if (!isUserGroupCreator(groupId, user)) {
            throw new CustomException(ErrorType.NOT_GROUP_CREATOR);
        }

        List<InvitedBoard> boards = invitedBoardRepository.findAllByStatus(InviteStatus.WAITING);

        return boards.stream()
                .map(InvitedBoardResponseDto::new)
                .collect(Collectors.toList());


    }

    private boolean isUserGroupCreator(long groupId, User user) {
        Meets meets = meetsRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_APPLY));
        return user.getId().equals(meets.getGroupCreator().getId());
    }
}
