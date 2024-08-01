package com.pallow.pallow.domain.auth.service;


import com.pallow.pallow.domain.auth.dto.*;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.common.CommonOauth;
import com.pallow.pallow.global.enums.CommonStatus;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Gender;
import com.pallow.pallow.global.enums.Role;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<String, Object> redisTemplate;


    private boolean isValidUsername(String username) {
        return username.matches("^[a-z0-9]{4,10}$");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^[a-zA-Z0-9]{8,15}$");
    }

    private final JavaMailSender mailSender;
    private static final String senderEmail = "pallow-company@gmail.com";

    @Transactional
    public AuthResponseDto signUp(AuthRequestDto authRequestDto) {
        if (userRepository.findByUsername(authRequestDto.getUsername()).isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_ACCOUNT_ID);
        } // 닉네임 유저아이디 유저네임 이메일 다 고유해야함
        CommonOauth oauth = CommonOauth.LOCAL;
        if ("KAKAO".equals(String.valueOf(authRequestDto.getOauth()))) {
            oauth = CommonOauth.KAKAO;
        }

        Gender gender = Gender.fromString(authRequestDto.getGender());

        User creadtedUser = User.createdUser(
                authRequestDto.getUsername(),
                authRequestDto.getNickname(),
                authRequestDto.getName(),
                authRequestDto.getEmail(),
                passwordEncoder.encode(authRequestDto.getPassword()),
                gender,
                Role.USER,
                oauth);
        userRepository.save(creadtedUser);
        return new AuthResponseDto(creadtedUser.getNickname(), creadtedUser.getEmail());
    }

    // authenticationManager 는 authenticate 메서드를 통해
    // UsernamePasswordAuthenticationToken 객체를 받아들이고,
    // 설정된 AuthenticationProvider 들을 사용하여 인증을 시도
    public Map<String, String> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword())
            ); //
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = findByUsername(loginRequestDto.getUsername());

            String token = issueTokenAndSave(user, response);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("username", loginRequestDto.getUsername());

            return responseData;
        } catch (AuthenticationException e) {
            throw new CustomException(ErrorType.LOGIN_FAILED);
        }
    }


    public String issueTokenAndSave(User user, HttpServletResponse response) {
        String newAccessToken = jwtProvider.createdAccessToken(user.getUsername(), user.getUserRole()); //
        String newRefreshToken = jwtProvider.createdRefreshToken(user.getUsername());
        // Access 토큰을 응답 헤더에 설정
        response.setHeader(JwtProvider.ACCESS_HEADER, newAccessToken);

        jwtProvider.setRefreshTokenCookie(response, newRefreshToken);
        // Refresh Token 을 Redis 에 저장
        saveRefreshToken(user.getUsername(), newRefreshToken, jwtProvider.getJwtRefreshExpiration());
        return newAccessToken;
    } // Refresh Token 만료 시간을 가져오기 위해서 JwtProvider 에서 생성자를 생성해서 가져옴

    public String tokenReIssue(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키에서 리프레시 토큰 가져오기
        String refreshToken = jwtProvider.getRefreshTokenFromCookie(request);

        // 토큰 유효성 검사
        jwtProvider.checkJwtToken(refreshToken);

        String refreshUsername = jwtProvider.getUserNameFromJwtToken(refreshToken);

        User user = findByUsername(refreshUsername);

        String storedRefreshToken = getRefreshToken(refreshUsername); // 리프레시토큰이 만료됬을경우 Null 을 반환

        // 저장된 리프레시 토큰이 null인 경우 (만료되었거나 존재하지 않음)
        if (storedRefreshToken == null) {
            throw new CustomException(ErrorType.TOKEN_CHECK_EXPIRED);
        }

        // Redis 에 저장된 리프레시 토큰이 "Bearer "로 시작하는지 확인하고 제거
        if (storedRefreshToken.startsWith("Bearer ")) {
            storedRefreshToken = storedRefreshToken.substring(7);
        }

        // 저장된 리프레시 토큰과 현재 리프레시 토큰 비교
        if (!refreshToken.equals(storedRefreshToken)) {
            throw new CustomException(ErrorType.TOKEN_MISMATCH);
        }

        // 새로운 액세스 토큰 발급
        String newAccessToken = issueTokenAndSave(user, response);
        response.setHeader(JwtProvider.ACCESS_HEADER, newAccessToken);
        return newAccessToken;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtProvider.getJwtFromHeader(request, JwtProvider.ACCESS_HEADER);
        String username = jwtProvider.getUserNameFromJwtToken(accessToken);
        deleteRefreshToken(username);
        SecurityContextHolder.clearContext();
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);  // 쿠키 만료
        response.addCookie(cookie);
    }

    //TODO : N+1 최적화 필요
    // --------------- 해당 클래스에서 사용되어지는 메서드 ---------------
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        // 해당 유저가 있으면 (전 : if 구문에서 예외처리로 확인 -> 수정)
        if (user.getStatus() == CommonStatus.DELETED) { // 유저가 이미 삭제된 유저라면
            throw new CustomException(ErrorType.USER_ALREADY_DELETED);
        }
        return user;
    }

    public void saveRefreshToken(String username, String refreshToken, long duration) {
        redisTemplate.opsForValue().set(username, refreshToken, duration, TimeUnit.MILLISECONDS);
        //username = 키 , refreshToken = 값, duration = 시간, TimeUnit.MILLISECONDS = 단위
    }

    public String getRefreshToken(String username) {
        return (String) redisTemplate.opsForValue().get(username);
    }

    public void deleteRefreshToken(String username) {
        redisTemplate.delete(username);
    }


}
