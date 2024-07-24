package com.pallow.pallow.domain.like.repository;

import com.pallow.pallow.domain.like.entity.Likes;
import com.pallow.pallow.global.enums.ContentType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByContentTypeAndContentId(ContentType contentType, Long contentId);
}
