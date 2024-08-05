package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.profile.enums.Region;
import com.pallow.pallow.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileRequestDto {

    @NotNull(message = "본인 소개가 필요합니다.")
    private String content;
    @NotNull(message = "생일을 입력해 주세요.")
    private String birth;
    @NotNull(message = "지역을 선택해 주세요.")
    private Region position;
    private Mbti mbti;
    private MultipartFile image;
    private int districtCode;
    private String districtCodeString;


    public Profile toEntity(User foundUser, String imageUrl) {
        return Profile.builder().birth(birth).content(content).position(position).mbti(mbti)
                .image(imageUrl).createdBy(foundUser).build();
    }
}
