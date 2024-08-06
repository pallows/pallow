package com.pallow.pallow.domain.profile.controller;

import com.pallow.pallow.domain.profile.dto.ProfileFlaskReseponseDto;
import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.enums.Mbti;
import com.pallow.pallow.domain.profile.service.ProfileService;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
//    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

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

    @GetMapping("/")
    public ResponseEntity<CommonResponseDto> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto responseDto = profileService.getMyProfile(userDetails.getUser().getId());
        return ResponseEntity.ok(new CommonResponseDto(Message.PROFILE_READ_SUCCESS, responseDto));
    }

    @PostMapping
    public ResponseEntity<CommonResponseDto> createProfile(
            @ModelAttribute("ProfileRequestDto") @Valid ProfileRequestDto requestDto,
            @RequestParam("username") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_USER)
        );
        ProfileResponseDto responseDto = profileService.createProfile(requestDto,
                user);
        return ResponseEntity.ok(
                new CommonResponseDto(Message.PROFILE_CREATE_SUCCESS, responseDto));
    }

//    @PostMapping
//    public ResponseEntity<CommonResponseDto> createProfile(
//            @RequestParam("content") String content,
//            @RequestParam("birth") String birth,
//            @RequestParam("mbti") String mbti,
//            @RequestParam("hobby") String hobby,
//            @RequestParam("photo") MultipartFile photo,
//            @RequestParam(value = "defaultPhoto", required = false) String defaultPhoto,
//            @RequestParam("position") String position,
//            @RequestParam("username") String username) throws IOException {
//
//        Mbti mbtiEnum = Mbti.valueOf(mbti);
//
//        String photoPath;
//        if (photo.isEmpty() && defaultPhoto != null && !defaultPhoto.isEmpty()) {
//            photoPath = defaultPhoto;
//        } else {
//            photoPath = saveFile(photo);
//        }
//
//        ProfileRequestDto requestDto = new ProfileRequestDto();
//        requestDto.setContent(content);
//        requestDto.setBirth(birth);
//        requestDto.setMbti(mbtiEnum);
//        requestDto.setPhoto(photoPath);
//        requestDto.setPosition(position);
//        requestDto.setUsername(username);
//        requestDto.setHobby(hobby);
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
//
//        ProfileResponseDto responseDto = profileService.createProfile(requestDto, user);
//        return ResponseEntity.ok(
//                new CommonResponseDto(Message.PROFILE_CREATE_SUCCESS, responseDto));
//    }
//
//    private String saveFile(MultipartFile file) throws IOException {
//        if (file.isEmpty()) {
//            throw new IOException("Failed to store empty file.");
//        }
//
//        String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(
//                Objects.requireNonNull(file.getOriginalFilename()));
//        Path uploadPath = Paths.get(UPLOAD_DIR);
//
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        Path filePath = uploadPath.resolve(fileName);
//        Files.copy(file.getInputStream(), filePath);
//        return "/images/" + fileName;
//    }

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
        if (userId == 0) {userId = userDetails.getUser().getId();}
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

    @GetMapping("/nearest")
    public ResponseEntity<CommonResponseDto> getNearest9Users(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<ProfileResponseDto> nearest9Users = profileService.getNearest9Users(user);
        return ResponseEntity.ok(new CommonResponseDto(Message.PROFILE_READ_SUCCESS, nearest9Users));
    }

//    @GetMapping("/recommendations")
//    public ResponseEntity<CommonResponseDto> getRecommendedProfiles(
//            @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        List<ProfileFlaskReseponseDto> responseDto = profileService.recommendProfiles(
//                userDetails.getUser());
//        return ResponseEntity.ok(
//                new CommonResponseDto(Message.PROFILE_RECOMMENDATION_SUCCESS, responseDto));
//    }

}
