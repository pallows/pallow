package com.pallow.pallow.domain.invitedboard.controller;

import com.pallow.pallow.domain.invitedboard.dto.InvitedBoardRequestDto;
import com.pallow.pallow.domain.invitedboard.dto.InvitedBoardResponseDto;
import com.pallow.pallow.domain.invitedboard.service.InvitedBoardService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/groups/{groupId}/invitation")
@RequiredArgsConstructor
public class InvitedBoardController {

    private final InvitedBoardService invitedBoardService;

    @PostMapping
    public ResponseEntity<CommonResponseDto> applyForGroup(
            @PathVariable("groupId") long groupId,
            @RequestBody InvitedBoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (invitedBoardService.declinedUser(userDetails.getUser(), groupId)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new CommonResponseDto(Message.DECLINED_APPLY_SUCCESS));
        }
        invitedBoardService.applyForGroup(groupId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.APPLY_FOR_GROUP_SUCCESS));
    }

    @PostMapping("/{userId}/accept")
    public ResponseEntity<CommonResponseDto> acceptApply(
            @PathVariable("groupId") long groupId,
            @PathVariable("userId") long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) { // 현재 접속한 유저
        invitedBoardService.acceptApply(groupId, userId, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.ACCEPT_APPLY_SUCCESS));
    }

    @PostMapping("/{userId}/decline")
    public ResponseEntity<CommonResponseDto> declineApply(
            @PathVariable("groupId") long groupId,
            @PathVariable("userId") long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        invitedBoardService.declineApply(groupId, userId, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.DECLINE_APPLY_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto> getAllInvitation(
            @PathVariable("groupId") long groupId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<InvitedBoardResponseDto> responseDtos = invitedBoardService.getAllInvitation(groupId, userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.INVITATION_READ_SUCCESS, responseDtos));
    }

}
