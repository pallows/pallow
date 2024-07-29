package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.enums.Mbti;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDto {

    @NotNull(message = "본인 소개가 필요합니다.")
    private String content;
    @NotNull(message = "생일을 입력해 주세요.")
    private LocalDate birth;

    private Mbti mbti;
    private String hobby;
    private String photo;
    private String position;
    private String username;

    public Profile toEntity(User foundUser) {
        return Profile.builder().birth(birth).content(content).mbti(mbti).photo(photo)
                .hobby(hobby).position(position).createdBy(foundUser).build();
    }
}
