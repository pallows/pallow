package com.pallow.pallow.domain.invitedboard.service;

import com.pallow.pallow.domain.invitedboard.dto.InvitedBoardRequestDto;
import com.pallow.pallow.domain.invitedboard.entity.InvitedBoard;
import com.pallow.pallow.domain.invitedboard.repository.InvitedBoardRepository;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.meets.repository.MeetsRepository;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public void acceptApply(long groupId, User user) {
        InvitedBoard invitedBoard = invitedBoardRepository.findById(groupId)
                .orElseThrow(()-> new CustomException(ErrorType.NOT_FOUND_APPLY));

        invitedBoard.acceptInvite();
    }
}
