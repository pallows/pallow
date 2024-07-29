package com.pallow.pallow.domain.auth.service;

import com.pallow.pallow.domain.auth.dto.AuthRequestDto;
import com.pallow.pallow.domain.auth.dto.AuthResponseDto;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.domain.user.service.UserService;
import com.pallow.pallow.global.common.CommonOauth;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Role;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Value("${kakao.clientId}")
    private String clientId;

    @Value("${kakao.redirectUri}")
    private String redirectUri;

    public void redirectToProvider(String provider, HttpServletResponse response) { // 추후 소셜 로그인 확장성을 위한 작성
        String redirectUrl;
        if (provider.equals("kakao")) {
            redirectUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code";
        } else {
            throw new CustomException(ErrorType.UNSUPPORTED_PROVIDE_FORBIDDEN);
        }
        response.setHeader("Location", redirectUrl);
        response.setStatus(302);
    }

    public String getAccessToken(String provider, String code) {
        if (provider.equals("kakao")) {
            return getKakaoAccessToken(code);
        } else {
            throw new CustomException(ErrorType.UNSUPPORTED_PROVIDE_FORBIDDEN);
        }
    }

    private String getKakaoAccessToken(String code) {
        String kakaoTokenUrl = "https://kauth.kakao.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();
        //HttpHeaders 객체 생성
        HttpHeaders headers = new HttpHeaders();

        // Content-Type 헤더 설정 APPLICATION_FORM_URLENCODED -> 카카오 API에 액세스 토큰을 요청할 때, 본문 데이터를 URL 인코딩 형식으로 전송해야된다.
        // application/x-www-form-urlencoded MIME 타입을 의미
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);


        // HttpEntity 객체 생성 (헤더와 본문 데이터를 포함)
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        // RestTemplate 을 사용하여 POST 요청 전송 및 응답 처리

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                kakaoTokenUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );
        // 응답 본문에서 액세스 토큰 추출
        Map<String, Object> responseBody = response.getBody();
        return (String) Objects.requireNonNull(responseBody).get("access_token");
    }

    private Map<String, Object> getKakaoUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    public Map<String, Object> getUserInfo(String provider, String accessToken) {
        if (provider.equals("kakao")) {
            return getKakaoUserInfo(accessToken);
        } else {
            throw new CustomException(ErrorType.UNSUPPORTED_PROVIDE_FORBIDDEN);
        }
    }


    public String login(String provider, String code, HttpServletResponse response) throws IOException {
        String accessToKen = getAccessToken(provider, code);
        Map<String, Object> userinfo = getUserInfo(provider, accessToKen);
        //당장 가져올 수 있는게 닉네임 정도라서 닉네임으로 유저를 판별
        String nickname = getNicknameUserInfo(provider, userinfo);
        User user;
        try {
            user = userRepository.findByNickname(nickname).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        } catch (CustomException e) {
            String encodedString = URLEncoder.encode("김도훈", StandardCharsets.UTF_8);
            response.sendRedirect("/signup?nickname=" + encodedString + "&oauth=KAKAO");
            return null;
        }
        return authService.issueTokenAndSave(user, response);
    }


    public String getNicknameUserInfo(String provider, Map<String, Object> userInfo) {
        if (provider.equals("kakao")) {
            // 'properties' 키에 해당하는 값 가져오기
            Object propertiesObject = userInfo.get("properties");

            // propertiesObject 가 Map<String, Object> 타입인지 확인 후 캐스팅
            if (propertiesObject instanceof Map) {
                @SuppressWarnings("unchecked")
                //컴파일러 경고 무시
                Map<String, Object> properties = (Map<String, Object>) propertiesObject;
                return (String) properties.get("nickname");
            } else {
                throw new CustomException(ErrorType.UNSUPPORTED_PROVIDE_FORBIDDEN);
            }
        } else {
            throw new CustomException(ErrorType.UNSUPPORTED_PROVIDE_FORBIDDEN);
        }
    }

    public String oauthSignUp(AuthRequestDto requestDto, HttpServletResponse response) {
        if (userRepository.findByNickname(requestDto.getNickname()).isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_ACCOUNT_ID);
        }

        User user = User.createdUser(
                requestDto.getUsername(),
                requestDto.getNickname(),
                requestDto.getEmail(),
                passwordEncoder.encode(requestDto.getPassword()),
                Role.USER,
                CommonOauth.KAKAO
        );
        userRepository.save(user);
        return authService.issueTokenAndSave(user, response);
    }


}
