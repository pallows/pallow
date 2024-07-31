package com.pallow.pallow.domain.profile.service;

import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.repository.ProfileRepository;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.region.District;
import com.pallow.pallow.global.region.Region;
import com.pallow.pallow.global.region.Region.District_Busan;
import com.pallow.pallow.global.region.Region.District_Chungcheongbuk;
import com.pallow.pallow.global.region.Region.District_Chungcheongnam;
import com.pallow.pallow.global.region.Region.District_Daegeon;
import com.pallow.pallow.global.region.Region.District_Daegu;
import com.pallow.pallow.global.region.Region.District_Gangwon;
import com.pallow.pallow.global.region.Region.District_Gwangju;
import com.pallow.pallow.global.region.Region.District_Gyeongi;
import com.pallow.pallow.global.region.Region.District_Gyeongsangbuk;
import com.pallow.pallow.global.region.Region.District_Gyeongsangnam;
import com.pallow.pallow.global.region.Region.District_Incheon;
import com.pallow.pallow.global.region.Region.District_Jeju;
import com.pallow.pallow.global.region.Region.District_Jeollabuk;
import com.pallow.pallow.global.region.Region.District_Jeollanam;
import com.pallow.pallow.global.region.Region.District_Seoul;
import com.pallow.pallow.global.region.Region.District_Ulsan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    /**
     * TODO 인증 인가 부분과 병합 후 메소드 작동시 유저 확인 받게끔 코드 변경 필요!
     */
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileResponseDto getProfile(Long userId) {
        Profile foundUser = profileRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        return new ProfileResponseDto(foundUser);
    }

    public ProfileResponseDto createProfile(ProfileRequestDto requestDto, User user) {
        User foundUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        requestDto.setDistrictCodeString(changeIntToString(requestDto.getDistrictCode()));
        Profile profile = profileRepository.save(requestDto.toEntity(foundUser));
        return new ProfileResponseDto(profile);
    }

    @Transactional
    public ProfileResponseDto updateProfile(Long userId, ProfileRequestDto requestDto, User user) {
        Profile foundUser = profileRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        if (!isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }
        requestDto.setDistrictCodeString(changeIntToString(requestDto.getDistrictCode()));
        foundUser.update(requestDto);
        return new ProfileResponseDto(foundUser);
    }

    @Transactional
    public void deleteProfile(Long userId, User user) {
        if (!isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }
        profileRepository.deleteById(userId);
    }

    private boolean isSameIdAndUser(Long userId, User user) {
        return user.getId().equals(userId);
    }

    /**
     * District Int -> String
     * @param code
     * @return String
     */
    private String changeIntToString(int code) {

        String temp = String.valueOf(code);
        int getRegionCode = Integer.parseInt(temp.substring(0, 2));

        switch (getRegionCode) {
            case 11:
                District_Seoul districtSeoul = District.findByDistrictCode(District_Seoul.class,
                        code);
                return String.valueOf(districtSeoul);
            case 21:
                District_Busan districtBusan = District.findByDistrictCode(District_Busan.class,
                        code);
                return String.valueOf(districtBusan);
            case 22:
                District_Daegu districtDaegu = District.findByDistrictCode(District_Daegu.class,
                        code);
                return String.valueOf(districtDaegu);
            case 23:
                District_Incheon districtIncheon = District.findByDistrictCode(
                        District_Incheon.class,
                        code);
                return String.valueOf(districtIncheon);
            case 24:
                District_Gwangju districtGwangju = District.findByDistrictCode(
                        District_Gwangju.class,
                        code);
                return String.valueOf(districtGwangju);
            case 25:
                District_Daegeon districtDaegeon = District.findByDistrictCode(
                        District_Daegeon.class,
                        code);
                return String.valueOf(districtDaegeon);
            case 26:
                District_Ulsan districtUlsan = District.findByDistrictCode(District_Ulsan.class,
                        code);
                return String.valueOf(districtUlsan);
            case 29:
                return "세종특별자치시";
            case 31:
                District_Gyeongi districtGyeongi = District.findByDistrictCode(
                        District_Gyeongi.class,
                        code);
                return String.valueOf(districtGyeongi);
            case 32:
                District_Gangwon districtGangwon = District.findByDistrictCode(
                        District_Gangwon.class,
                        code);
                return String.valueOf(districtGangwon);
            case 33:
                District_Chungcheongbuk districtChungcheongbuk = District.findByDistrictCode(
                        District_Chungcheongbuk.class,
                        code);
                return String.valueOf(districtChungcheongbuk);
            case 34:
                District_Chungcheongnam districtChungcheongnam = District.findByDistrictCode(
                        District_Chungcheongnam.class,
                        code);
                return String.valueOf(districtChungcheongnam);
            case 35:
                District_Jeollabuk districtJeollabuk = District.findByDistrictCode(
                        District_Jeollabuk.class,
                        code);
                return String.valueOf(districtJeollabuk);
            case 36:
                District_Jeollanam districtJeollanam = District.findByDistrictCode(
                        District_Jeollanam.class,
                        code);
                return String.valueOf(districtJeollanam);
            case 37:
                District_Gyeongsangbuk districtGyeongsangbuk = District.findByDistrictCode(
                        District_Gyeongsangbuk.class,
                        code);
                return String.valueOf(districtGyeongsangbuk);
            case 38:
                District_Gyeongsangnam districtGyeongsangnam = District.findByDistrictCode(
                        District_Gyeongsangnam.class,
                        code);
                return String.valueOf(districtGyeongsangnam);
            case 39:
                District_Jeju districtJeju = District.findByDistrictCode(District_Jeju.class,
                        code);
                return String.valueOf(districtJeju);
        }
        throw new CustomException(ErrorType.NOT_FOUND_REGION);
    }

}
