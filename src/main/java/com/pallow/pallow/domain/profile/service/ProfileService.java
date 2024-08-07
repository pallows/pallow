package com.pallow.pallow.domain.profile.service;

import com.pallow.pallow.domain.profile.dto.ProfileFlaskResponseDto;
import com.pallow.pallow.domain.profile.dto.ProfileMapper;
import com.pallow.pallow.domain.profile.dto.ProfileRequestDto;
import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.entity.Profile;
import com.pallow.pallow.domain.profile.entity.ProfileItem;
import com.pallow.pallow.domain.profile.repository.ProfileRepository;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.dtos.FlaskRequestDto;
import com.pallow.pallow.global.dtos.FlaskResponseDto;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * TODO 인증 인가 부분과 병합 후 메소드 작동시 유저 확인 받게끔 코드 변경 필요!
     */
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;

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

    @Transactional
    public List<ProfileFlaskResponseDto> recommendProfiles(User user) {
        user = userRepository.save(user);
        // save 로 할지, findBy 로 할지, em.persist 로 할지
        Profile currentUserProfile = profileRepository.findByUserId(user.getId());
        List<Profile> profileList = profileRepository.findAllByPosition(user.getProfile().getPosition());
        List<ProfileItem> items = new ArrayList<>();
        // cascade = cascadeType.persist 영속성 컨텍스트 걸어주는게 요걸로 알고 있는데 문제는 어디에 걸어야할질 모름   그 user나 profile에 <- 저거 걸어주는걸론 안되나요?
        // 어우야 ... 모쪼록 감사합니다...
        // 어 그 profile에 이미 해놨는데 아하 ?
        profileList.forEach(profile -> items.add(profileMapper.toRequestItem(profile)));

        user = userRepository.findById(user.getId()).orElseThrow();
        // 넵! 감사합니다!
        // 요 아래거 다 해봤는데 안됐어요  넹  gpt도 해보고 인터넷 검색도 해봤는데 안돼서 꺾임 ㅋㅋㅋㅋ 인가 없어서 그런건가요?
        // 진짜 눈앞에 다이아가 있는데 돌아가버렷네
//        트랜잭션이 제대로 걸려 있는지 다시 비즈니스 로직을 검토한다.
//        필요한 부분에 대해서 단방향 연관 관계를 맺는다.
//        @EntityGraph 어노테이션을 통해 연관관계를 명확히 지정해준다.
        // 근데 요 아래거 했을때 안되던데
//        UserRepository의 findById를 통해 명시적으로 가져온다. (이렇게 되면 sql 연산이 2번 날라가서 비효율적이긴 함.)
//        @OneToOne 어노테이션으로 직접 엔티티를 정의한 것 외로도, Private Long userId;라는 멤버 변수를 선언하여 조회 시에는 해당 멤버 변수를 이용해 조인한다.

        // 그 원래 user에 담아도 될 정보인데 그냥 따로 profile 이란 클래스 만들어서 관리하려고 onetoone 되어 있는 정보

        // 로그 추가: 전송 데이터 확인
        log.info("Items to be sent to Flask: {}", items);
        log.info("Sending request to Flask with data: {}", FlaskRequestDto.builder()
                .id(currentUserProfile.getId())
                .profiles(items)
                .build());

        // send request to flask
        FlaskResponseDto responseDto = sendRequestToFlask(FlaskRequestDto.builder()
                .id(currentUserProfile.getId())
                .profiles(items)
                .build());

        // 로그 추가: 응답 데이터 확인
        log.info("Received response from Flask: {}", responseDto);
        log.info("Received sorted ID list from Flask: {}", responseDto.getData().getSortedIdList());

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
                    "http://localhost:8000/api/profile/recommend",
                    new HttpEntity<>(requestDto, headers),
                    FlaskResponseDto.class
            );

            // 로그 추가: 요청과 응답 정보 확인
            log.info("Request sent to Flask: {}", requestDto);
            log.info("Response received from Flask: {}", responseEntity.getBody());

            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            log.error("HTTP error while sending request to Flask: {}", e.getMessage());
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }
    }


    private boolean isSameIdAndUser(Long userId, User user) {
        return user.getId().equals(userId);
    }
}
