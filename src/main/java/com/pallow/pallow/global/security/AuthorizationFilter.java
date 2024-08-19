package com.pallow.pallow.global.security;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallow.pallow.domain.user.service.RefreshTokenService;
import com.pallow.pallow.global.dtos.AuthenticatedResponse;
import com.pallow.pallow.global.dtos.UnauthenticatedResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = tokenProvider.getAccessToken(req);
        String refreshToken = tokenProvider.getRefreshToken(req);
        log.info("refreshToken : {} ", refreshToken);
        if (StringUtils.hasText(accessToken)) {
            // 액세스 토큰 검증
            if (tokenProvider.validateAccessToken(accessToken)) {  // 엑세스토큰 유효성 검사
                String username = tokenProvider.getUsernameFromAccessToken(accessToken); // 엑세스 서버에서 토큰 검증 및 유저네임 추출
                log.info("액세스 토큰 검증 성공");
                setAuthentication(username);
            } else if (StringUtils.hasText(refreshToken)) {
                log.info("액세스 토큰 만료");
                // 리프레시 토큰 검증
                String username = tokenProvider.getUsernameFromAccessToken(refreshToken);
                if (username != null && refreshTokenService.refreshTokenValidation(username, refreshToken)) {
                    log.info("리프레시 토큰 검증 성공");

                    log.info("새로운 액세스 토큰, 리프레시 토큰 발급 (RTR)");
                    String newRefreshToken = tokenProvider.createRefreshToken(username);
                    String newAccessToken = tokenProvider.createAccessToken(username);

                    log.info("리프레시 토큰 쿠키에 저장");
                    tokenProvider.saveRefreshTokenToCookie(newRefreshToken, res);

                    verifiedRefreshTokenHandler(res, newAccessToken);

                    log.info("액세스 토큰 응답");
                    res.addHeader(TokenProvider.ACCESS_TOKEN_HEADER, newAccessToken);
                    setAuthentication(username);
                } else {
                    log.error("리프레시 토큰 검증 실패");
                    // 리프레시 토큰이 유효하지 않다면 삭제할 필요가 없음
                    // 검증 실패 처리
                    unverifiedRefreshTokenHandler(res);
                }
                return;
            }
        }
        filterChain.doFilter(req, res);
    }

    /**
     * 인증 처리
     */
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(createAuthentication(username));
        SecurityContextHolder.setContext(context);
    }

    /**
     * 인증 객체 생성
     */
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
    }

    /**
     * Refresh 토큰 검증 성공 핸들러
     */
    public void verifiedRefreshTokenHandler(HttpServletResponse res, String newAccessToken)
            throws IOException {
        res.setStatus(SC_OK);
        res.setContentType("application/json");
        String json = new ObjectMapper().writeValueAsString(
                new AuthenticatedResponse(newAccessToken, "리프레시 토큰 검증 성공 & 새로운 토큰 발급")
        );
        res.getWriter().write(json);
    }

    /**
     * Refresh 토큰 검증 실패 핸들러
     */
    public void unverifiedRefreshTokenHandler(HttpServletResponse res) throws IOException {
        res.setStatus(SC_UNAUTHORIZED);
        res.setContentType("application/json");
        String json = new ObjectMapper().writeValueAsString(
                new UnauthenticatedResponse("리프레시 토큰 검증 실패")
        );
        res.getWriter().write(json);
    }

}