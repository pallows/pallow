package com.pallow.pallow.domain.profile.controller;

import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.service.ProfileService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<CommonResponseDto> getProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto responseDto = profileService.getProfile(userDetails.getUser().getId());
        return ResponseEntity.ok(new CommonResponseDto(Message.PROFILE_READ_SUCCESS, responseDto));
    }

    /**
     * 프로필 생성
     *
     * @param requestDto  생성 데이터
     * @param userDetails 유저 데이터
     * @return 생성 성공 메시지 + 생성된 프로필 데이터
     */
    @PostMapping
    public ResponseEntity<CommonResponseDto> createProfile(
            @RequestBody @Valid ProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto responseDto = profileService.createProfile(requestDto,
                userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.PROFILE_CREATE_SUCCESS, responseDto));
    }

    @PatchMapping
    public ResponseEntity<CommonResponseDto> updateProfile(
            @RequestBody @Valid ProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto responseDto = profileService.updateProfile(userDetails.getUser().getId(), requestDto,
                userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.PROFILE_UPDATE_SUCCESS, responseDto));
    }

    @DeleteMapping
    public ResponseEntity<CommonResponseDto> deleteProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        profileService.deleteProfile(userDetails.getUser().getId(), userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.PROFILE_DELETE_SUCCESS));
    }

}
