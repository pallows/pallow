package com.pallow.pallow.domain.auth.service;


import com.pallow.pallow.domain.auth.dto.*;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.common.CommonOauth;
import com.pallow.pallow.global.enums.CommonStatus;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Role;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
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

    @Transactional
    public AuthResponseDto signUp(AuthRequestDto authRequestDto) {
        if (userRepository.findByUsername(authRequestDto.getUsername()).isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_ACCOUNT_ID);
        } // 닉네임 유저아이디 유저네임 이메일 다 고유해야함
        CommonOauth oauth = CommonOauth.LOCAL;
        if ("KAKAO".equals(String.valueOf(authRequestDto.getOauth()))) {
            oauth = CommonOauth.KAKAO;
        }
        User creadtedUser = User.createdUser(
                authRequestDto.getUsername(),
                authRequestDto.getNickname(),
                authRequestDto.getName(),
                authRequestDto.getEmail(),
                passwordEncoder.encode(authRequestDto.getPassword()),
                authRequestDto.getGender(),
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

    public void logout(HttpServletRequest request) {
        String accessToken = jwtProvider.getJwtFromHeader(request, JwtProvider.ACCESS_HEADER);
        String username = jwtProvider.getUserNameFromJwtToken(accessToken);
        deleteRefreshToken(username);
        SecurityContextHolder.clearContext();
    }

//    public String sendMail(EmailInputRequestDto emailInputRequestDto) {
//        log.info("이메일 요청 받은것 {}", emailInputRequestDto.getEmail());
//        String code = generateVerificationCode();
//        ValueOperations<String, Object> emailAndCode = redisTemplate.opsForValue();
//        emailAndCode.set(emailInputRequestDto.getEmail(), code, 5, TimeUnit.MINUTES);
//
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(emailInputRequestDto.getEmail());
//            message.setSubject("Email 인증");
//            message.setText("인증 코드 : " + code);
//            javaMailSender.send(message);
//        } catch (Exception e) {
//            log.error("메일 전송 오류: ", e);
//            throw new CustomException(ErrorType.MAIL_NOT_SEND); // CustomException 으로 적절한 에러 처리
//        }
//        return code;
//    }

//    public String verifyCode(EmailCodeRequestDto emailCodeRequestDto) {
//        ValueOperations<String, Object> emailAndCode = redisTemplate.opsForValue();
//        if ((emailAndCode.get(emailCodeRequestDto.getEmail())) instanceof String) {
//            String storedCode = (String) emailAndCode.get(emailCodeRequestDto.getEmail());
//            if (storedCode != null && storedCode.equals(emailCodeRequestDto.getCode())) {
//                return "True";
//            }
//        }
//        log.info("이메일 인증 실패");
//        throw new CustomException(ErrorType.MAIL_MISMATCH_OR_CODE_FORBIDDEN);
//    }


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

    private String generateVerificationCode() {
        Random randomCode = new Random(); // Random 클래스 생성 무작위 수를 위한 유틸리티 클래스
        int code = 100000 + randomCode.nextInt(900000);
        // randomCode.nextInt(900000) : 0~899999 사이의 랜덤한 정수를 생성
        // nextInt 는 지정된 범위 내 무작위 숫자 반환
        // 앞에 있는 100000은 생성되는 랜덤 정수에 더하는 수 즉 100000~999999 까지의 랜덤한 정수 생성
        return String.valueOf(code);
        // 그 수를 문자열화 해서 return
    }


}
