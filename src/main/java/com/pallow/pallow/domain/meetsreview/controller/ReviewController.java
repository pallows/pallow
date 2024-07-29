package com.pallow.pallow.domain.meetsreview.controller;

import com.pallow.pallow.domain.meets.service.MeetsService;
import com.pallow.pallow.domain.meetsreview.dto.ReviewRequestDto;
import com.pallow.pallow.domain.meetsreview.service.ReviewService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meets/{meets_id}/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final MeetsService meetsService;

    /**
     * 유지영 수정
     * 모임 리뷰 생성
     * @param meets_id  그룹 ID
     * @param requestDto  생성 데이터 [content, starRating]
     * @param userDetails 유저 데이터
     * @return 생성 성공 메시지 + 생성된 리뷰 데이터
     */
    @PostMapping()
    public ResponseEntity<CommonResponseDto> createReview(
            @PathVariable Long meets_id,
            @RequestBody ReviewRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (!meetsService.isParticipant(meets_id, userDetails.getUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new CommonResponseDto(Message.UNAUTHORIZED_REVIEW));
        }
        reviewService.create(meets_id, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.REVIEW_CREATE_SUCCESS));
    }

    /**
     * 모임 리뷰 단건 조회?
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
     * 모임 리뷰 전체 조회
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
     * 모임 리뷰 업데이트
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
