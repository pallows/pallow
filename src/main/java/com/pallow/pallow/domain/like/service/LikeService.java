package com.pallow.pallow.domain.like.service;

import com.pallow.pallow.domain.like.entity.Likeable;
import com.pallow.pallow.domain.like.entity.Likes;
import com.pallow.pallow.domain.like.repository.LikesRepository;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikesRepository likesRepository;

    @Transactional
    public <T extends Likeable> void toggleLike(Long contentId, User user, JpaRepository<T, Long> repository) {
        // 컨텐츠가 있는지 확인
        T content = repository.findById(contentId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_CONTENT)
        );
        // 좋아요가 이미 있는지 검사
        Optional<Likes> existingLike = likesRepository.findByContentTypeAndContentIdAndUser(content.contentType(), content.getId(), user);

        if (existingLike.isEmpty()) {
            // 좋아요가 없는 경우 생성
            Likes like = Likes.builder()
                    .contentType(content.contentType())
                    .contentId(content.getId())
                    .user(user)
                    .build();

            likesRepository.save(like);
            content.addLikesCount();
        } else {
            // 좋아요가 이미 있는 경우 삭제
            likesRepository.delete(existingLike.get());
            content.minusLikesCount();
        }
    }
}
