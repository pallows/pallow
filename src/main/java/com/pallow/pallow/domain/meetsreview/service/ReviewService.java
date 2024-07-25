package com.pallow.pallow.domain.meetsreview.service;

import com.pallow.pallow.domain.invitedboard.service.InvitedBoardService;
import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.meets.service.MeetsService;
import com.pallow.pallow.domain.meetsreview.dto.ReviewRequestDto;
import com.pallow.pallow.domain.meetsreview.dto.ReviewResponseDto;
import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import com.pallow.pallow.domain.meetsreview.repository.ReviewRepository;
import com.pallow.pallow.domain.user.entity.User;
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
    private final InvitedBoardService invitedBoardService;

    /**
     * 리뷰 생성
     */
    public ReviewResponseDto create(Long meetsId, ReviewRequestDto requestDto, User user) {
        // 그룹이 존재하는지 확인
        Meets meets = meetsService.findByMeetsIdAndStatus(meetsId);

        // 유저가 그룹에 속해있는지 확인
        if (!meets.getGroupCreator().getId().equals(user.getId()) && !invitedBoardService.isUserInGroup(user, meets)) {
            throw new CustomException(ErrorType.NOT_FOUND_USER_IN_GROUP);
        }

        MeetsReview meetsReview = MeetsReview.builder()
                .content(requestDto.getContent())
                .likesCount(0)
                .user(user)
                .meets(meets)
                .build();

        reviewRepository.save(meetsReview);

        return new ReviewResponseDto(meetsReview);
    }

    /**
     * 리뷰 선택 조회
     */
    public ReviewResponseDto getReview(Long meetsId, Long reviewId) {
        MeetsReview review = getValidatedMeetsAndReview(meetsId, reviewId);
        return new ReviewResponseDto(review);
    }

    /**
     * 리뷰 전체 조회
     */
    public List<ReviewResponseDto> getAllReview(Long meetsId) {
        List<MeetsReview> reviewList = reviewRepository.findAllByMeetsId(meetsId);
        return reviewList.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 리뷰 업데이트
     */
    @Transactional
    public ReviewResponseDto update(Long meetsId, Long reviewId, ReviewRequestDto requestDto,
            User user) {
        MeetsReview review = getValidatedMeetsAndReview(meetsId, reviewId);

        // 리뷰 작성자인지 검사
        if (!user.getId().equals(review.getUser().getId())) {
            throw new CustomException(ErrorType.NOT_AUTHORIZED_UPDATE);
        }

        MeetsReview updatedReview = review.update(requestDto);
        return new ReviewResponseDto(updatedReview);
    }

    /**
     * 리뷰 삭제
     */
    public void delete(Long meetsId, Long reviewId, User user) {
        MeetsReview review = getValidatedMeetsAndReview(meetsId, reviewId);

        // 리뷰 작성자인지 검사
        if (!user.getId().equals(review.getUser().getId())) {
            throw new CustomException(ErrorType.NOT_AUTHORIZED_DELETE);
        }

        reviewRepository.delete(review);
    }

    /**
     * 모임과 리뷰 검사
     */
    private MeetsReview getValidatedMeetsAndReview(Long meetsId, Long reviewId) {
        // 모임의 존재, 상태 검사
        Meets meets = meetsService.findByMeetsIdAndStatus(meetsId);

        // 리뷰가 존재하는지 확인
        MeetsReview review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_REVIEW)
        );

        // 리뷰가 해당 모임에 속해있는지 확인
        if (!review.getMeets().getId().equals(meetsId)) {
            throw new CustomException(ErrorType.INVALID_REVIEW_FOR_MEET);
        }

        return review;
    }

//    public MeetsReview findById(Long contentId) {
//        return reviewRepository.findById(contentId).orElseThrow(
//                () -> new CustomException(ErrorType.NOT_FOUND_REVIEW)
//        );
//    }

}
