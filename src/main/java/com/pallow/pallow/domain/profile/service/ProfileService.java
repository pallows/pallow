package com.pallow.pallow.domain.profile.service;


import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.repository.ProfileRepository;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
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
//    private final ProfileMapper profileMapper;

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
  
//    @Transactional
//    public List<ProfileFlaskReseponseDto> recommendProfiles(User user) {
//        Profile currentUserProfile = profileRepository.findByUserId(user.getId());
//        List<Profile> profileList = profileRepository.findAllByPosition(user.getProfile().getPosition());
//        List<ProfileItem> items = new ArrayList<>();
//
//        profileList.forEach(profile -> items.add(profileMapper.toRequestItem(profile)));
//
//        // 로그 추가: 전송 데이터 확인
//        log.info("Items to be sent to Flask: {}", items);
//        log.info("Sending request to Flask with data: {}", FlaskRequestDto.builder()
//                .id(currentUserProfile.getId())
//                .profiles(items)
//                .build());
//
//        // send request to flask
//        FlaskResponseDto responseDto = sendRequestToFlask(FlaskRequestDto.builder()
//                .id(currentUserProfile.getId())
//                .profiles(items)
//                .build());
//
//        // 로그 추가: 응답 데이터 확인
//        log.info("Received response from Flask: {}", responseDto);
//        log.info("Received sorted ID list from Flask: {}", responseDto.getData().getSortedIdList());
//
//        // make response
//        List<ProfileFlaskReseponseDto> results = new ArrayList<>();
//        responseDto.getData().getSortedIdList().forEach(id -> {
//            Profile profile = profileRepository.findById(id)
//                    .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
//            results.add(profileMapper.getResponseDto(profile));
//        });
//
//        // Ensure `results` is not empty before removing the first element
//        if (!results.isEmpty()) {
//            results.remove(0);
//        } else {
//            // Log or handle the empty results case if needed
//            log.warn("No profiles found to remove.");
//        }
//
//        return results;
//    }
//
//    private FlaskResponseDto sendRequestToFlask(FlaskRequestDto requestDto) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-type", "application/json; charset=UTF-8");
//
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<FlaskResponseDto> responseEntity = restTemplate.postForEntity(
//                    "http://localhost:8000/api/profile/recommend",
//                    new HttpEntity<>(requestDto, headers),
//                    FlaskResponseDto.class
//            );
//
//            // 로그 추가: 요청과 응답 정보 확인
//            log.info("Request sent to Flask: {}", requestDto);
//            log.info("Response received from Flask: {}", responseEntity.getBody());
//
//            return responseEntity.getBody();
//        } catch (HttpClientErrorException e) {
//            log.error("HTTP error while sending request to Flask: {}", e.getMessage());
//            throw new CustomException(ErrorType.NOT_FOUND_USER);
//        }
//    }


    private boolean isSameIdAndUser(Long userId, User user) {
        return user.getId().equals(userId);
    }
}
