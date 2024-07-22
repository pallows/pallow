package com.pallow.pallow.domain.meetsreview.service;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.meets.repository.MeetsRepository;
import com.pallow.pallow.domain.meets.service.MeetsService;
import com.pallow.pallow.domain.meetsreview.dto.ReviewRequestDto;
import com.pallow.pallow.domain.meetsreview.dto.ReviewResponseDto;
import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import com.pallow.pallow.domain.meetsreview.repository.ReviewRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MeetsService meetsService;

    public ReviewResponseDto create(Long meetsId, ReviewRequestDto requestDto) {
        Meets meets = meetsService.findByMeetsIdAndStatus(meetsId);

        MeetsReview meetsReview = MeetsReview.builder()
                .content(requestDto.getContent())
                .meets(meets)
                .build();

        reviewRepository.save(meetsReview);

        return new ReviewResponseDto(meetsReview);
    }

    public ReviewResponseDto getReview(Long meetsId, Long reviewId) {
        MeetsReview review = getValidatedMeetsReview(meetsId, reviewId);
        return new ReviewResponseDto(review);
    }

    public List<ReviewResponseDto> getAllReview(Long meetsId) {
        List<MeetsReview> reviewList = reviewRepository.findAllByMeetsId(meetsId);
        return reviewList.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewResponseDto update(Long meetsId, Long reviewId, ReviewRequestDto requestDto) {
        MeetsReview review = getValidatedMeetsReview(meetsId, reviewId);
        MeetsReview updatedReview = review.update(requestDto);
        return new ReviewResponseDto(updatedReview);
    }

    public void delete(Long meetsId, Long reviewId) {
        MeetsReview review = getValidatedMeetsReview(meetsId, reviewId);
        reviewRepository.delete(review);
    }

    private MeetsReview getValidatedMeetsReview(Long meetsId, Long reviewId) {
        Meets meets = meetsService.findByMeetsIdAndStatus(meetsId);

        // 리뷰가 존재하고, 해당 리뷰가 주어진 모임에 속하는지 확인
        MeetsReview review = reviewRepository.findByIdAndMeetsId(reviewId, meetsId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_REVIEW_ID)
        );

        return review;
    }

}
