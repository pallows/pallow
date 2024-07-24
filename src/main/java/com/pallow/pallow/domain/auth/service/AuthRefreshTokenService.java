package com.pallow.pallow.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthRefreshTokenService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveRefreshToken(String username, String refreshToken, long duration) {
        redisTemplate.opsForValue().set(username, refreshToken, duration, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(String username) {
        return (String) redisTemplate.opsForValue().get(username);
    }

    public void deleteRefreshToken(String username) {
        redisTemplate.delete(username);
    }
}
