package com.pallow.pallow.domain.userboard.controller;

import com.pallow.pallow.domain.userboard.dto.UserBoardRequestDto;
import com.pallow.pallow.domain.userboard.dto.UserBoardResponseDto;
import com.pallow.pallow.domain.userboard.service.UserBoardService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/userboards")
@RequiredArgsConstructor
public class UserBoardController {

    private final UserBoardService userBoardService;

    @PostMapping
    public ResponseEntity<CommonResponseDto> createUserBoard(
            @PathVariable("userId") long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid UserBoardRequestDto requestDto) {
        UserBoardResponseDto responseDto = userBoardService.createBoard(requestDto,
                userDetails.getUser(), userId);
        return ResponseEntity.ok(
                new CommonResponseDto(Message.USERBOARD_CREATE_SUCCESS, responseDto));
    }

    @GetMapping("/{userBoardId}")
    public ResponseEntity<CommonResponseDto> getUserBoard(
            @PathVariable("userId") long userId,
            @PathVariable("userBoardId") long userBoardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserBoardResponseDto responseDto = userBoardService.getBoard(userId, userBoardId,
                userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.USERBOARD_READ_SUCCESS, responseDto));
    }


}
