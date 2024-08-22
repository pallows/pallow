package com.pallow.pallow.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

    private final StringRedisTemplate stringRedisTemplate;

    @Transactional
    public void refreshTokenSave(String username, String refreshToken, long ttl) {
        // Redis 에 저장
        stringRedisTemplate.opsForValue().set(username, refreshToken, Duration.ofSeconds(ttl));
    }

    public boolean refreshTokenValidation(String username, String refreshToken) {
        // Redis 에서 username 을 키로 저장된 리프레시 토큰을 가져옴
        String storedRefreshToken = stringRedisTemplate.opsForValue().get(username);
        log.info("storedRefreshToken : {} ", storedRefreshToken);
        // 가져온 토큰과 전달받은 리프레시 토큰을 비교하여 결과 반환
        // 쿠키에서 가져온 리프레시 토큰에 JWT_PREFIX를 추가
        String tokenWithPrefix = "Bearer " + refreshToken;
        return tokenWithPrefix.equals(storedRefreshToken);
    }

    public void deleteRefreshToken(String username) {
        // Redis 에서 해당 유저의 리프레시 토큰 삭제
        stringRedisTemplate.delete(username);
    }
}