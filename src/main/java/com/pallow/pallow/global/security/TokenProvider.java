package com.pallow.pallow.global.security;

import com.pallow.pallow.domain.user.service.RefreshTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    public static final String JWT_PREFIX = "Bearer ";
    public static final String ACCESS_TOKEN_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_COOKIE = "refresh_token";
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.access-token.ttl}")
    private int accessTokenTtl;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.refresh-token.ttl}")
    private Long refreshTokenTtl;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * Access 토큰 생성
     */
    public String createAccessToken(String username) {
        Date date = new Date();
        return JWT_PREFIX + Jwts.builder()
                .setSubject(username)
                .setIssuedAt(date) // 발급일
                .setExpiration(new Date(date.getTime() + accessTokenTtl))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Access 토큰 생성
     */
    public String createRefreshToken(String username) {
        Date now = new Date();

        String refreshToken = JWT_PREFIX + Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenTtl)) // 밀리초 단위로 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Redis에 TTL을 초 단위로 변환하여 저장
        refreshTokenService.refreshTokenSave(username, refreshToken, refreshTokenTtl / 1000); // 밀리초를 초로 변환
        return refreshToken;
    }

    /**
     * Cookie에 Refresh 토큰 저장
     */
    public void saveRefreshTokenToCookie(String refreshToken, HttpServletResponse response) {
        String tokenWithoutPrefix = refreshToken.replace("Bearer ", "").trim();
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE, tokenWithoutPrefix)
                .httpOnly(true)
//                .secure(true)
                .path("/")
                .maxAge(accessTokenTtl)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

    }

    /**
     * Header에서 Access 토큰 가져오기
     */
    public String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(ACCESS_TOKEN_HEADER);
        log.info(bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JWT_PREFIX)) {
            return bearerToken.substring(7);
        } else {
            return null;
        }
    }

    /**
     * Cookie에서 Refresh 토큰 가져오기
     */
    public String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REFRESH_TOKEN_COOKIE)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * Access 토큰 검증
     */
    public boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(accessToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT 입니다.");
        }
        return false;
    }

    /**
     * Access 토큰에서 username 가져오기
     */
    public String getUsernameFromAccessToken(String accessToken) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(accessToken).getBody().getSubject();
    }

    public Long getRefreshTokenTtl() {
        return refreshTokenTtl;
    }

}