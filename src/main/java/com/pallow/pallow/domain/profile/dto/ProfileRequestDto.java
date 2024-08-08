package com.pallow.pallow.domain.profile.dto;

import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.enums.Alcohol;
import com.pallow.pallow.domain.profile.enums.Education;
import com.pallow.pallow.domain.profile.enums.Jobs;
import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.profile.enums.Personality;
import com.pallow.pallow.domain.profile.enums.Pros;
import com.pallow.pallow.domain.profile.enums.Relationship;
import com.pallow.pallow.domain.profile.enums.Religion;
import com.pallow.pallow.domain.profile.enums.Smoking;
import com.pallow.pallow.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import java.lang.reflect.Method;
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
    private String position;
    private Mbti mbti;
    private String hobby;
    private MultipartFile image;

    private String interest;
    private String alcohol;
    private String education;
    private String ideal;
    private String jobs;
    private String personality;
    private String relationship;
    private String religion;
    private String smoking;
    private String pros;

    // 프로필 화면에 띄우기 위함
    private String username;

    public Profile toEntity(User foundUser, String imageUrl) {
        return Profile.builder()
                .birth(birth).hobby(hobby).content(content).position(position).mbti(mbti)
                .image(imageUrl).createdBy(foundUser).interest(interest).ideal(ideal)
                .alcohol(convertToEnum(Alcohol.class, alcohol)).education(convertToEnum(Education.class, education))
                .jobs(convertToEnum(Jobs.class, jobs)).personality(convertToEnum(Personality.class, personality))
                .relationship(convertToEnum(Relationship.class, relationship)).religion(convertToEnum(Religion.class, religion))
                .smoking(convertToEnum(Smoking.class, smoking)).pros(convertToEnum(Pros.class, pros)).build();
    }

    @SuppressWarnings("unchecked")
    private <E extends Enum<E>> E convertToEnum(Class<E> enumClass, String value) {
        try {
            Method valueOfMethod = enumClass.getMethod("valueOf", String.class);
            return (E) valueOfMethod.invoke(null, value);
        } catch (Exception e) {
            return null;
        }
    }
}