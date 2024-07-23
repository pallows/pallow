package com.pallow.pallow.domain.like.service;

import com.pallow.pallow.domain.like.dto.LikesRequestDto;
import com.pallow.pallow.domain.like.entity.Likes;
import com.pallow.pallow.domain.like.repository.LikesRepository;
import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import com.pallow.pallow.domain.meetsreview.repository.ReviewRepository;
import com.pallow.pallow.domain.meetsreview.service.ReviewService;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.enums.ContentType;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void createLike(Long reviewId, LikesRequestDto requestDto, User user) {
        // 리뷰가 있는지 확인
        MeetsReview review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_REVIEW)
        );

        // 컨텐츠 타입과 컨텐츠 아이디
        ContentType contentType = ContentType.MEETS_REVIEW;
        Long contentId = review.getId();

        //이미 좋아요를 했는지 검사
        if (likesRepository.findByContentTypeAndContentId(contentType, contentId).isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_LIKE);
        }

        Likes likes = Likes.builder()
                .contentType(contentType)
                .contentId(contentId)
                .user(user)
                .build();

        likesRepository.save(likes);

        review.addLikesCount();
    }

    @Transactional
    public void deleteLike(Long reviewId, LikesRequestDto requestDto, User user) {
        // 리뷰가 있는지 확인
        MeetsReview review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_REVIEW)
        );

        // 컨텐츠 타입과 컨텐츠 아이디
        ContentType contentType = ContentType.MEETS_REVIEW;
        Long contentId = review.getId();

        //좋아요가 있는지 검사
        Likes likes = likesRepository.findByContentTypeAndContentId(contentType, contentId)
                .orElseThrow(
                        () -> new CustomException(ErrorType.NOT_FOUND_LIKE)
                );

        likesRepository.delete(likes);

        review.minusLikesCount();
    }
}
