package com.pallow.pallow.domain.invitedboard.controller;

import com.pallow.pallow.domain.invitedboard.dto.InvitedBoardRequestDto;
import com.pallow.pallow.domain.invitedboard.entity.InvitedBoard;
import com.pallow.pallow.domain.invitedboard.service.InvitedBoardService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        invitedBoardService.applyForGroup(groupId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.APPLY_FOR_GROUP_SUCCESS));
    }

}
