package com.pallow.pallow.domain.profile.controller;

import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.repository.ProfileRepository;
import com.pallow.pallow.domain.profile.service.ProfileService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles/{userId}")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    /**
     * 프로필 조회
     *
     * @param userId 유저아이디
     * @return 조회 성공 메시지 + 조회 데이터
     */
    @GetMapping
    public ResponseEntity<CommonResponseDto> getProfile(
            @PathVariable Long userId) {
        ProfileResponseDto responseDto = profileService.getProfile(userId);
        return ResponseEntity.ok(new CommonResponseDto(Message.PROFILE_READ_SUCCESS, responseDto));
    }

    /**
     * 프로필 생성
     *
     * @param requestDto  생성 데이터
     * @param userDetails 유저 데이터
     * @return 생성 성공 메시지 + 생성된 프로필 데이터
     */
    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<CommonResponseDto> createProfile(
            @ModelAttribute @Valid ProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto responseDto = profileService.createProfile(requestDto,
                userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.PROFILE_CREATE_SUCCESS, responseDto));
    }

    /**
     * 프로필 수정
     *
     * @param requestDto  수정 데이터
     * @param userDetails 수정하는 유저 데이터
     * @param userId      수정하려는 유저 데이터
     * @return 수정 성공 메시지 + 수정된 프로필 데이터
     */
    @PatchMapping
    public ResponseEntity<CommonResponseDto> updateProfile(
            @ModelAttribute @Valid ProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long userId) {
        ProfileResponseDto responseDto = profileService.updateProfile(userId, requestDto,
                userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.PROFILE_UPDATE_SUCCESS, responseDto));

    }

    @DeleteMapping
    public ResponseEntity<CommonResponseDto> deleteProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long userId) {
        profileService.deleteProfile(userId, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.PROFILE_DELETE_SUCCESS));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<CommonResponseDto> getRecommendedProfiles(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long userId) {
        Profile profile = profileRepository.findById(userId).orElseThrow(() -> new CustomException(
                ErrorType.NOT_FOUND_USER));
        List<ProfileResponseDto> responseDto = profileService.recommendProfiles(profile,
                userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.PROFILE_RECOMMENDATION_SUCCESS, responseDto));
    }

}
