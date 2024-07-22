package com.pallow.pallow.domain.meetsreview.controller;

import com.pallow.pallow.domain.meets.dto.MeetsRequestDto;
import com.pallow.pallow.domain.meetsreview.dto.ReviewRequestDto;
import com.pallow.pallow.domain.meetsreview.service.ReviewService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping()
    public ResponseEntity<CommonResponseDto> createReview(@PathVariable Long meets_id,
            @RequestBody ReviewRequestDto requestDto) {
        return ResponseEntity.ok(
                new CommonResponseDto(
                        Message.REVIEW_CREATE_SUCCESS, reviewService.create(meets_id, requestDto)));
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<CommonResponseDto> getReview(@PathVariable Long meets_id,
            @PathVariable Long review_id) {
        return ResponseEntity.ok(
                new CommonResponseDto(
                        Message.REVIEW_READ_SUCCESS,
                        reviewService.getReview(meets_id, review_id)));
    }

    @GetMapping()
    public ResponseEntity<CommonResponseDto> getAllReview(@PathVariable Long meets_id) {
        return ResponseEntity.ok(
                new CommonResponseDto(
                        Message.REVIEW_READ_SUCCESS, reviewService.getAllReview(meets_id)));
    }

    @PatchMapping("/{review_id}")
    public ResponseEntity<CommonResponseDto> updateReview(@PathVariable Long meets_id,
            @PathVariable Long review_id,
            @RequestBody ReviewRequestDto requestDto) {
        return ResponseEntity.ok(
                new CommonResponseDto(
                        Message.REVIEW_UPDATE_SUCCESS,
                        reviewService.update(meets_id, review_id, requestDto)));
    }

    @DeleteMapping("/{review_id}")
    public ResponseEntity<CommonResponseDto> updateReview(@PathVariable Long meets_id,
            @PathVariable Long review_id) {
        reviewService.delete(meets_id, review_id);
        return ResponseEntity.ok(
                new CommonResponseDto( Message.REVIEW_DELETE_SUCCESS));
    }
}
