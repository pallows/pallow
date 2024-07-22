package com.pallow.pallow.domain.userboardcomment.controller;

import com.pallow.pallow.domain.userboard.dto.UserBoardResponseDto;
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
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
            @PathVariable long userId,
            @PathVariable long userBoardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid UserBoardCommentRequestDto requestDto) {
        UserBoardCommentResponseDto responseDto = userBoardCommentService.createComment(userId,
                userBoardId, userDetails.getUser(), requestDto);
        return ResponseEntity.ok(
                new CommonResponseDto(Message.COMMENT_CREATE_SUCCESS, responseDto));
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto> getAllComment(
            @PathVariable long userId,
            @PathVariable long userBoardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<UserBoardCommentResponseDto> responseDtos = userBoardCommentService.getComments(userId,
                userBoardId, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.COMMENT_READ_SUCCESS, responseDtos));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto> updateComment(
            @PathVariable long userId,
            @PathVariable long userBoardId,
            @PathVariable long commentId,
            @RequestBody @Valid UserBoardCommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserBoardCommentResponseDto responseDto = userBoardCommentService.updateComment(userId,
                userBoardId, commentId, userDetails.getUser(), requestDto);
        return ResponseEntity.ok(
                new CommonResponseDto(Message.COMMENT_UPDATE_SUCCESS, responseDto));

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComment(
            @PathVariable long userId,
            @PathVariable long userBoardId,
            @PathVariable long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userBoardCommentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.COMMNET_DELETE_SUCCESS));
    }

}
