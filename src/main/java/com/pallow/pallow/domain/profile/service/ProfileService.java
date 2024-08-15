package com.pallow.pallow.domain.profile.service;


import com.pallow.pallow.domain.profile.dto.ProfileFlaskResponseDto;
import com.pallow.pallow.domain.profile.dto.ProfileMapper;
import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.entity.ProfileItem;
import com.pallow.pallow.domain.profile.repository.ProfileCustomRepository;
import com.pallow.pallow.domain.profile.repository.ProfileRepository;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.dtos.FlaskRequestDto;
import com.pallow.pallow.global.dtos.FlaskResponseDto;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.s3.service.ImageService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    @Value("${FLASK_URL}")
    private String flaskServerUrl;

    private final ImageService imageService;

    /**
     * TODO 인증 인가 부분과 병합 후 메소드 작동시 유저 확인 받게끔 코드 변경 필요!
     */
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;
    private final ProfileCustomRepository profileCustomRepository;

    public ProfileResponseDto getProfile(Long userId) {
        Profile foundUser = profileRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        return new ProfileResponseDto(foundUser, foundUser.getUser().getUsername(), foundUser.getUser().getNickname());
    }

    public ProfileResponseDto createProfile(ProfileRequestDto requestDto, User user, String defaultImage) {
        String imageUrl;
        if (requestDto.getImage() == null || requestDto.getImage().isEmpty()) {
            imageUrl = defaultImage;
        } else {
            try {
                imageUrl = imageService.imageUpload(requestDto.getImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Profile profile = profileRepository.save(requestDto.toEntity(user, imageUrl));
        return new ProfileResponseDto(profile, profile.getUser().getName(), profile.getUser().getNickname());
    }

    @Transactional
    public ProfileResponseDto updateProfile(Long userId, ProfileRequestDto requestDto, User user) {
        Profile foundUser = profileRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));

        if (!isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }
        String imageUrl;
        if (requestDto.getImage() != null && !requestDto.getImage().isEmpty()) {
            try {
                imageService.deleteImage(foundUser.getImage());
                imageUrl = imageService.imageUpload(requestDto.getImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            imageUrl = foundUser.getImage();
        }
        foundUser.update(requestDto, imageUrl);
        return new ProfileResponseDto(foundUser, foundUser.getUser().getName(), foundUser.getUser().getNickname());
    }

    @Transactional
    public void deleteProfile(Long userId, User user) {
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_USER)
        );

        if (!isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }

        // 이미지 삭제
        if (profile.getImage() != null && !profile.getImage().isEmpty()) {
            imageService.deleteImage(profile.getImage());
        }

        profileRepository.deleteById(userId);
    }

    @Transactional
    public List<ProfileFlaskResponseDto> recommendProfiles(User user) {

        user = userRepository.save(user);

        Optional<Profile> currentUserProfile = profileRepository.findByUserId(user.getId());

        List<Profile> profileList = profileRepository.findAllByPositionAndUserStatus(
                user.getProfile().getPosition(), user.getStatus());

        List<ProfileItem> items = new ArrayList<>();
        profileList.forEach(profile -> items.add(profileMapper.toRequestItem(profile)));

        // send request to flask
        FlaskResponseDto responseDto = sendRequestToFlask(FlaskRequestDto.builder()
                .id(currentUserProfile.get().getId())
                .profiles(items)
                .build());

        // make response
        List<ProfileFlaskResponseDto> results = new ArrayList<>();
        responseDto.getData().getSortedIdList().forEach(id -> {
            Profile profile = profileRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
            results.add(profileMapper.getResponseDto(profile));
        });

        // Ensure `results` is not empty before removing the first element
        if (!results.isEmpty()) {
            results.remove(0);
        } else {
            // Log or handle the empty results case if needed
            log.warn("No profiles found to remove.");
        }

        return results;
    }


    private FlaskResponseDto sendRequestToFlask(FlaskRequestDto requestDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/json; charset=UTF-8");

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<FlaskResponseDto> responseEntity = restTemplate.postForEntity(
                    flaskServerUrl + "/api/profile/recommend",
                    new HttpEntity<>(requestDto, headers),
                    FlaskResponseDto.class
            );

            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            log.error("HTTP error while sending request to Flask: {}", e.getMessage());
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }
    }


    private boolean isSameIdAndUser(Long userId, User user) {
        return user.getId().equals(userId);
    }

    public List<ProfileResponseDto> getNearProfiles(Long userId) {
        Profile foundUser = profileRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        String[] positionParts = foundUser.getPosition().split(" ");

        String first = positionParts.length > 0 ? positionParts[0].trim() : "";
        String second = positionParts.length > 1 ? positionParts[1].trim() : "";
        String third = positionParts.length > 2 ? positionParts[2].trim() : "";
        return profileCustomRepository.findTop9NearestProfiles(userId, first, second, third);
    }
}
