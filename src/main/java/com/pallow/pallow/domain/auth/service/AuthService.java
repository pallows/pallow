package com.pallow.pallow.domain.auth.service;


import com.pallow.pallow.domain.auth.dto.AuthRequestDto;
import com.pallow.pallow.domain.auth.dto.AuthResponseDto;
import com.pallow.pallow.domain.auth.dto.LoginRequestDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.CommonStatus;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Role;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Transactional
    public AuthResponseDto signUp(AuthRequestDto authRequestDto) {
        if (userRepository.findByUsername(authRequestDto.getUsername()).isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_ACCOUNT_ID);
        } // 닉네임 유저아이디 유저네임 이메일 다 고유해야함
        User creadtedUser = User.createdUser(
                authRequestDto.getUsername(),
                authRequestDto.getNickname(),
                authRequestDto.getEmail(),
                passwordEncoder.encode(authRequestDto.getPassword()),
                Role.USER);
        userRepository.save(creadtedUser);
        return new AuthResponseDto(creadtedUser.getNickname(), creadtedUser.getEmail());
    }

    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        User user = findByUsername(loginRequestDto.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername()
                        , loginRequestDto.getPassword())
        );
        // authenticationManager 는 authenticate 메서드를 통해
        // UsernamePasswordAuthenticationToken 객체를 받아들이고,
        // 설정된 AuthenticationProvider 들을 사용하여 인증을 시도
        SecurityContextHolder.getContext().setAuthentication(authentication);
        issueTokenAndSave(user, response);
    }

    private void issueTokenAndSave(User user, HttpServletResponse response) {
        String newAccessToken = jwtProvider.createdAccessToken(user.getUsername(), user.getUserRole());
        String newRefreshToken = jwtProvider.createdRefreshToken(user.getUsername());
        // Access 토큰을 응답 헤더에 설정
        response.setHeader(JwtProvider.ACCESS_HEADER, newAccessToken);
        // Refresh Token 을 Redis 에 저장
        saveRefreshToken(user.getUsername(), newRefreshToken, jwtProvider.getJwtRefreshExpiration());
    } // Refresh Token 만료 시간을 가져오기 위해서 JwtProvider 에서 생성자를 생성해서 가져옴

    public void tokenReIssue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtProvider.getJwtFromHeader(request, JwtProvider.REFRESH_HEADER);
        jwtProvider.checkJwtToken(refreshToken); // 토큰 유효성 검사
        String refreshUsername = jwtProvider.getUserNameFromJwtToken(refreshToken);
        User user = findByUsername(refreshUsername);
        String storedRefreshToken = getRefreshToken(refreshUsername);
        log.info("현재있는 토큰 {}", refreshToken);
        log.info("저장된리프레시 토큰 {}", storedRefreshToken);
        String token = storedRefreshToken.substring(7);
        if (!token.equals(refreshToken)) {
            throw new CustomException(ErrorType.TOKEN_MISMATCH);
        }
        issueTokenAndSave(user, response);
    }

    public void logout(HttpServletRequest request) {
        String accessToken = jwtProvider.getJwtFromHeader(request, JwtProvider.ACCESS_HEADER);
        String username = jwtProvider.getUserNameFromJwtToken(accessToken);
        deleteRefreshToken(username);
        SecurityContextHolder.clearContext();
    }

    //TODO : N+1 최적화 필요
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
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
