package com.pallow.pallow.domain.profile.service;

import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.repository.ProfileRepository;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
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

}
