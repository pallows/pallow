package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.user.entity.User;
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
    private String birth;
    @NotNull(message = "지역을 입력해 주세요.")
    private String position;
    private Mbti mbti;
    private String photo;


    public Profile toEntity(User foundUser) {
        return Profile.builder().birth(birth).content(content).position(position).mbti(mbti)
                .photo(photo).createdBy(foundUser).build();
    }
}