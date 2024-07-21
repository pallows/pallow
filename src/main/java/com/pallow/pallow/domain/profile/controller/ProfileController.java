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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 프로필 조회
     *
     * @param userId 유저아이디
     * @return
     */
    @GetMapping
    public ResponseEntity<CommonResponseDto> getProfile(
            @PathVariable Long userId) {
        ProfileResponseDto responseDto = profileService.getProfile(userId);
        return ResponseEntity.ok(new CommonResponseDto(Message.PROFILE_READ_SUCCESS, responseDto));
    }

    @PostMapping
    public ResponseEntity<CommonResponseDto> createProfile(
            @RequestBody @Valid ProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto responseDto = profileService.createProfile(requestDto,
                userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.PROFILE_CREATE_SUCCESS, responseDto));
    }

}
