package com.pallow.pallow.domain.userboardcomment.controller;

import com.pallow.pallow.domain.userboardcomment.dto.UserBoardCommentRequestDto;
import com.pallow.pallow.domain.userboardcomment.dto.UserBoardCommentResponseDto;
import com.pallow.pallow.domain.userboardcomment.service.UserBoardCommentService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
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
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/userboards/{userBoardId}/comments")
public class UserBoardCommentController {

    private final UserBoardCommentService userBoardCommentService;

    @PostMapping
    public ResponseEntity<CommonResponseDto> createComment(
            @PathVariable Long userId,
            @PathVariable Long userBoardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid UserBoardCommentRequestDto requestDto) {
        UserBoardCommentResponseDto responseDto = userBoardCommentService.createComment(userId,
                userBoardId, userDetails.getUser(), requestDto);
        return ResponseEntity.ok(
                new CommonResponseDto(Message.COMMENT_CREATE_SUCCESS, responseDto));
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto> getAllComment(
            @PathVariable Long userId,
            @PathVariable Long userBoardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<UserBoardCommentResponseDto> responseDtos = userBoardCommentService.getComments(userId,
                userBoardId, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.COMMENT_READ_SUCCESS, responseDtos));
    }

}
