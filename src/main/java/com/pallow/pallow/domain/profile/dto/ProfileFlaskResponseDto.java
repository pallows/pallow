package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.enums.Mbti;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileFlaskResponseDto {

    private Long id;
    private String name;
    private Mbti mbti;
    private String image;
    private String nickname;
}
