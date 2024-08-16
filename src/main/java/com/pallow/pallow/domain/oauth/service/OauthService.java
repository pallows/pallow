package com.pallow.pallow.domain.oauth.service;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.domain.user.service.RefreshTokenService;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.security.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

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
        log.info("Redirect URL: {}", redirectUrl);
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


    public Map<String, String> login(String provider, String code, HttpServletResponse response) {
        String accessToken = getAccessToken(provider, code);
        Map<String, Object> userinfo = getUserInfo(provider, accessToken);
        //카카오에서 카카오 계정의 고유 id를 가져옵니다.

        Long kakaoId = ((Number) userinfo.get("id")).longValue();
        User user = userRepository.findBykakaoId(kakaoId).orElse(null);
        log.info("kakaoId : {}", kakaoId);
        log.info("카카오 유저 아이디 : {}", user);
        Map<String, String> responseMap = new HashMap<>();
        log.info("responseMap : {}", responseMap);
        if (user == null) {
            // 유저가 없으면 회원가입 페이지로 리다이렉션
            responseMap.put("redirectUrl", "/public/signup?kakaoId=" + kakaoId + "&oauth=KAKAO");
            return responseMap;
        }
        log.info("카카오 유저 아이디가 존재합니다 : {} ", user);
        // 유저가 존재하는 경우, 토큰 생성 후 반환
        // Authenticate user with PreAuthenticatedAuthenticationToken
        PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(
                user.getUsername(), null, AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Generate and set tokens
        String newAccessToken = tokenProvider.createAccessToken(user.getUsername());
        String refreshToken = UUID.randomUUID().toString();
        tokenProvider.saveRefreshTokenToCookie(refreshToken, response);
        refreshTokenService.save(user.getUsername(), refreshToken);

        response.setHeader(TokenProvider.ACCESS_TOKEN_HEADER, newAccessToken);

        responseMap.put("accessToken", newAccessToken);
        //    tokenProvider.createAccessToken(user.getUsername());
        return responseMap;
    }
}