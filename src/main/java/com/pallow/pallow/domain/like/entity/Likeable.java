package com.pallow.pallow.domain.like.entity;

import com.pallow.pallow.global.enums.ContentType;

public interface Likeable {
    Long getId();
    ContentType contentType();
    void addLikesCount();
    void minusLikesCount();
}
