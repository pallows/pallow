package com.pallow.pallow.domain.profile.controller;

import com.pallow.pallow.domain.profile.dto.ProfileFlaskResponseDto;
import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.service.ProfileService;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserRepository userRepository;

    /**
     * 프로필 조회
     *
     * @param userId 유저아이디
     * @return 조회 성공 메시지 + 조회 데이터
     */
    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponseDto> getProfile(@PathVariable Long userId) {
        ProfileResponseDto responseDto = profileService.getProfile(userId);
        return ResponseEntity.ok(new CommonResponseDto(Message.PROFILE_READ_SUCCESS, responseDto));
    }

    /**
     * 프로필 생성
     * @param requestDto  생성 데이터
     * @param username 유저 데이터
     * @param defaultImage 기본 이미지
     * @return 생성 성공 메시지 + 생성된 프로필 데이터
     */
    @PostMapping
    public ResponseEntity<CommonResponseDto> createProfile(
            @ModelAttribute("ProfileRequestDto") @Valid ProfileRequestDto requestDto, @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam("username") String username, @RequestParam(value = "defaultImage", required = false) String defaultImage) {
        User user;
        if (userId != null) {
            user = userRepository.findById(userId).orElseThrow(
                    () -> new CustomException(ErrorType.NOT_FOUND_USER));
        } else {
            user = userRepository.findByUsername(username).orElseThrow();
        }
        ProfileResponseDto responseDto = profileService.createProfile(requestDto, user, defaultImage);
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
    @PatchMapping("/{userId}")
    public ResponseEntity<CommonResponseDto> updateProfile(
            @ModelAttribute @Valid ProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long userId) {
        ProfileResponseDto responseDto = profileService.updateProfile(userId, requestDto,
                userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.PROFILE_UPDATE_SUCCESS, responseDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponseDto> deleteProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long userId) {
        profileService.deleteProfile(userId, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.PROFILE_DELETE_SUCCESS));
    }

    @GetMapping("/todays-friends")
    public ResponseEntity<CommonResponseDto> getRecommendedProfiles(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ProfileFlaskResponseDto> responseDto = profileService.recommendProfiles(
                userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.PROFILE_RECOMMENDATION_SUCCESS, responseDto));
    }

    @GetMapping("/near")
    public ResponseEntity<CommonResponseDto> getNearProfiles(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
            List<ProfileResponseDto> responseDto = profileService.getNearProfiles(userDetails.getUser().getId());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.PROFILE_READ_SUCCESS, responseDto));
    }

}
