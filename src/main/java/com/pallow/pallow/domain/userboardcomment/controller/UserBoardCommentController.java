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
public class UserBoardCommentController {

    private final UserBoardCommentService userBoardCommentService;

    @PostMapping("/users/{userId}/userboards/{userBoardId}/comments")
    public ResponseEntity<CommonResponseDto> createComment(
            @PathVariable long userBoardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid UserBoardCommentRequestDto requestDto) {
        UserBoardCommentResponseDto responseDto = userBoardCommentService.createComment(
                userBoardId, userDetails.getUser(), requestDto);
        return ResponseEntity.ok(
                new CommonResponseDto(Message.COMMENT_CREATE_SUCCESS, responseDto));
    }

    @GetMapping("/users/{userId}/userboards/{userBoardId}/comments")// -> board 쓸 유저 프로필까지 가져오기
    public ResponseEntity<CommonResponseDto> getAllComment(
            @PathVariable long userBoardId) {
        List<UserBoardCommentResponseDto> responseDtos = userBoardCommentService.getComments(
                userBoardId);
        return ResponseEntity.ok(new CommonResponseDto(Message.COMMENT_READ_SUCCESS, responseDtos));
    }

    @PatchMapping("/users/{userId}/userboards/{userBoardId}/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> updateComment(
            @PathVariable long commentId,
            @RequestBody @Valid UserBoardCommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserBoardCommentResponseDto responseDto = userBoardCommentService.updateComment(
                commentId, userDetails.getUser(), requestDto);
        return ResponseEntity.ok(
                new CommonResponseDto(Message.COMMENT_UPDATE_SUCCESS, responseDto));

    }

    @DeleteMapping("/users/{userId}/userboards/{userBoardId}/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComment(
            @PathVariable long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userBoardCommentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.COMMNET_DELETE_SUCCESS));
    }

    /**
     * 좋아요 토글
     *
     * @param commentId
     * @param userDetails
     * @return
     */
    @PostMapping("/userboardscomment/{commentId}/like")
    public ResponseEntity<CommonResponseDto> likeReview(@PathVariable Long commentId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userBoardCommentService.toggleLike(commentId, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.LIKES_TOGGLE_SUCCESS));
    }
}
