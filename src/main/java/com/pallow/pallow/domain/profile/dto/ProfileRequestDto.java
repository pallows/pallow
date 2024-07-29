package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.enums.Gender;
import com.pallow.pallow.global.enums.Mbti;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProfileRequestDto {

    @NotNull(message = "본인 소개가 필요합니다.")
    private String content;
    @NotNull(message = "생일을 입력해 주세요.")
    private String birth;

    private Mbti mbti;
    private String hobby;
    private String photo;
    private String position;

    public Profile toEntity(User foundUser) {
        return Profile.builder().birth(birth).content(content).mbti(mbti).photo(photo)
                .hobby(hobby).position(position).createdBy(foundUser).build();
    }
}
