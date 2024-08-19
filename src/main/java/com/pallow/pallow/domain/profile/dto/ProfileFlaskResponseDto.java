package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileFlaskResponseDto {

    private Long id;
    private String name;
    private Mbti mbti;
    private String image;
    private String nickName;
    // 여기에 닉네임이 들어가야
}
