package com.pallow.pallow.domain.profile.controller;

import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.service.ProfileService;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Mbti;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<CommonResponseDto> getProfile(
            @PathVariable Long userId) {
        ProfileResponseDto responseDto = profileService.getProfile(userId);
        return ResponseEntity.ok(new CommonResponseDto(Message.PROFILE_READ_SUCCESS, responseDto));
    }

    @PostMapping
    public ResponseEntity<CommonResponseDto> createProfile(
            @RequestParam("content") String content,
            @RequestParam("birth") String birth,
            @RequestParam("mbti") String mbti,
            @RequestParam("hobby") String hobby,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("position") String position,
            @RequestParam("username") String username) throws IOException {

        LocalDate birthDate = LocalDate.parse(birth);
        Mbti mbtiEnum = Mbti.valueOf(mbti);

        ProfileRequestDto requestDto = new ProfileRequestDto();
        requestDto.setContent(content);
        requestDto.setBirth(birthDate);
        requestDto.setMbti(mbtiEnum);
        requestDto.setHobby(hobby);
        requestDto.setPhoto(new String(photo.getBytes()));
        requestDto.setPosition(position);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));

        ProfileResponseDto responseDto = profileService.createProfile(requestDto, user);
        return ResponseEntity.ok(new CommonResponseDto(Message.PROFILE_CREATE_SUCCESS, responseDto));
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
            @RequestBody @Valid ProfileRequestDto requestDto,
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

}
