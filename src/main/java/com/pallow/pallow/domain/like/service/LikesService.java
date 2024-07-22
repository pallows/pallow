package com.pallow.pallow.domain.like.service;

import com.pallow.pallow.domain.like.dto.LikesRequestDto;
import com.pallow.pallow.domain.like.dto.LikesResponseDto;
import com.pallow.pallow.domain.like.entity.Likes;
import com.pallow.pallow.domain.like.repository.LikesRepository;
import com.pallow.pallow.domain.meetsreview.entity.MeetsReview;
import com.pallow.pallow.domain.meetsreview.service.ReviewService;
import com.pallow.pallow.global.enums.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final ReviewService reviewService;

    @Transactional
    public void createLike(Long review_id, LikesRequestDto requestDto) {
        MeetsReview review = reviewService.findById(review_id);

        ContentType contentType = requestDto.getContentType();
        Long contentId = requestDto.getContentId();

        // 타입 검사 + contentId 검사

        Likes likes = Likes.builder()
                .contentType(contentType)
                .contentId(contentId)
                .build();

        likesRepository.save(likes);

        review.addLikesCount();
//        return new LikesResponseDto(likes);
    }


}
