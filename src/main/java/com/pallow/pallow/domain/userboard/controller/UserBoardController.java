package com.pallow.pallow.domain.userboard.controller;

import com.pallow.pallow.domain.userboard.dto.UserBoardRequestDto;
import com.pallow.pallow.domain.userboard.dto.UserBoardResponseDto;
import com.pallow.pallow.domain.userboard.service.UserBoardService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserBoardController {

    private final UserBoardService userBoardService;

    @PostMapping("/users/{userId}/userboards")
    public ResponseEntity<CommonResponseDto> createUserBoard(
            @PathVariable("userId") long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute @Valid UserBoardRequestDto requestDto) {
        UserBoardResponseDto responseDto = userBoardService.createBoard(requestDto,
                userDetails.getUser(), userId);
        return ResponseEntity.ok(
                new CommonResponseDto(Message.USERBOARD_CREATE_SUCCESS, responseDto));
    }

    @GetMapping("/users/{userId}/userboards")
    public ResponseEntity<CommonResponseDto> getAllUserBoard(
            @PathVariable("userId") long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<UserBoardResponseDto> responseDtos = userBoardService.getBoards(userId,
                userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.USERBOARD_READ_SUCCESS, responseDtos));
    }

    @GetMapping("/users/{userId}/userboards/{userBoardId}")
    public ResponseEntity<CommonResponseDto> getUserBoard(
            @PathVariable("userId") long userId,
            @PathVariable("userBoardId") long userBoardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserBoardResponseDto responseDto = userBoardService.getBoard(userId, userBoardId,
                userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.USERBOARD_READ_SUCCESS, responseDto));
    }

    @PatchMapping("/users/{userId}/userboards/{userBoardId}")
    public ResponseEntity<CommonResponseDto> updateUserBoard(
            @PathVariable("userId") long userId,
            @PathVariable("userBoardId") long userBoardId,
            @ModelAttribute @Valid UserBoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserBoardResponseDto responseDto = userBoardService.updateUserBoard(userId, userBoardId,
                requestDto, userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.USERBOARD_UPDATE_SUCCESS, responseDto));
    }

    @DeleteMapping("/users/{userId}/userboards/{userBoardId}")
    public ResponseEntity<CommonResponseDto> deleteUserBoard(
            @PathVariable("userId") long userId,
            @PathVariable("userBoardId") long userBoardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userBoardService.deleteUserBoard(userId, userBoardId, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.USERBOARD_DELETE_SUCCESS));
    }

    /**
     * 좋아요 토글
     *
     * @param userboard_id
     * @param userDetails
     * @return
     */
    @PostMapping("/userboards/{userboard_id}/like")
    public ResponseEntity<CommonResponseDto> likeReview(@PathVariable Long userboard_id,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userBoardService.toggleLike(userboard_id, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.LIKES_TOGGLE_SUCCESS));
    }

    /**
     * 오늘의 동친 (무작위 9개)
     */
    @GetMapping("/todays-friends")
    public ResponseEntity<CommonResponseDto> getTodaysFriends(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<UserBoardResponseDto> todaysFriends = userBoardService.getTodaysFriends(userDetails.getUser(), 9);
        return ResponseEntity.ok(new CommonResponseDto(Message.USERBOARD_READ_SUCCESS, todaysFriends));
    }
}
