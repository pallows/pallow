package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.profile.enums.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileFlaskReseponseDto {

    private Long id;
    private String content;
    private String birth;
    private Region position;
    private String district;
    private Mbti mbti;
    private String photo;

}
