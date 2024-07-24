package com.pallow.pallow.domain.meetsreview.controller;

import com.pallow.pallow.domain.meetsreview.dto.ReviewRequestDto;
import com.pallow.pallow.domain.meetsreview.service.ReviewService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.security.UserDetailsImpl;
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
@RequestMapping("/meets/{meets_id}/review")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 모임 리뷰 생성
     * @param meets_id  그룹 ID
     * @param requestDto  생성 데이터 [content]
     * @param userDetails 유저 데이터
     * @return 생성 성공 메시지 + 생성된 리뷰 데이터
     */
    @PostMapping()
    public ResponseEntity<CommonResponseDto> createReview(@PathVariable Long meets_id,
            @RequestBody ReviewRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new CommonResponseDto(
                        Message.REVIEW_CREATE_SUCCESS, reviewService.create(meets_id, requestDto, userDetails.getUser())));
    }

    /**
     * 모임 리뷰 생성
     * @param meets_id  그룹 ID
     * @param review_id 리뷰 ID
     * @return 조회 성공 메시지 + 데이터
     */
    @GetMapping("/{review_id}")
    public ResponseEntity<CommonResponseDto> getReview(@PathVariable Long meets_id,
            @PathVariable Long review_id) {
        return ResponseEntity.ok(
                new CommonResponseDto(
                        Message.REVIEW_READ_SUCCESS,
                        reviewService.getReview(meets_id, review_id)));
    }

    /**
     * 모임 리뷰 생성
     * @param meets_id  그룹 ID
     * @return 조회 성공 메시지 + 데이터
     */
    @GetMapping()
    public ResponseEntity<CommonResponseDto> getAllReview(@PathVariable Long meets_id) {
        return ResponseEntity.ok(
                new CommonResponseDto(
                        Message.REVIEW_READ_SUCCESS, reviewService.getAllReview(meets_id)));
    }

    /**
     * 모임 리뷰 생성
     * @param meets_id  그룹 ID
     * @param review_id 리뷰 ID
     * @param requestDto  변경할 데이터 [content]
     * @param userDetails 유저 데이터
     * @return 업데이트 성공 메시지 + 변경된 리뷰 데이터
     */
    @PatchMapping("/{review_id}")
    public ResponseEntity<CommonResponseDto> updateReview(@PathVariable Long meets_id,
            @PathVariable Long review_id,
            @RequestBody ReviewRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new CommonResponseDto(
                        Message.REVIEW_UPDATE_SUCCESS,
                        reviewService.update(meets_id, review_id, requestDto, userDetails.getUser())));
    }

    /**
     * 모임 리뷰 생성
     * @param meets_id  그룹 ID
     * @param review_id 리뷰 ID
     * @param userDetails 유저 데이터
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/{review_id}")
    public ResponseEntity<CommonResponseDto> updateReview(@PathVariable Long meets_id,
            @PathVariable Long review_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.delete(meets_id, review_id, userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto( Message.REVIEW_DELETE_SUCCESS));
    }
}
