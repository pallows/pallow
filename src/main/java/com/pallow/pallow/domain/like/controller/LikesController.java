package com.pallow.pallow.domain.like.controller;

import com.pallow.pallow.domain.like.dto.LikesRequestDto;
import com.pallow.pallow.domain.like.service.LikesService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    /**
     * 게시물 좋아요
     */
    @PostMapping("/review/{review_id}/like")
    public ResponseEntity<CommonResponseDto> createLike(@PathVariable Long review_id,
            @RequestBody LikesRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        likesService.createLike(review_id, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.LIKES_CREATE_SUCCESS));
    }

    @DeleteMapping("/review/{review_id}/like")
    public ResponseEntity<CommonResponseDto> deleteLike(@PathVariable Long review_id,
            @RequestBody LikesRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        likesService.deleteLike(review_id, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.LIKES_CREATE_SUCCESS));
    }
}
