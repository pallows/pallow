package com.pallow.pallow.domain.user.service;

import com.pallow.pallow.domain.user.entity.RefreshToken;
import com.pallow.pallow.domain.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void save(String username, String refreshToken) {
        refreshTokenRepository.save(new RefreshToken(username, refreshToken));
    }

    @Transactional
    public void delete(String refreshToken) {
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

}