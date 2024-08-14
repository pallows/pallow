package com.pallow.pallow.global.security;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallow.pallow.domain.user.dto.LoginRequestDto;
import com.pallow.pallow.domain.user.service.RefreshTokenService;
import com.pallow.pallow.global.dtos.UnauthenticatedResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationFilter(TokenProvider tokenProvider,
            RefreshTokenService refreshTokenService) {
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        log.info("로그인 시도");
        try {
            // json to object
            LoginRequestDto dto = new ObjectMapper().readValue(req.getInputStream(),
                    LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
            FilterChain chain, Authentication authResult) throws IOException {

        Long userId = ((UserDetailsImpl) authResult.getPrincipal()).getId();

        if (((UserDetailsImpl) authResult.getPrincipal()).getUser().getProfile() == null) {
            res.setStatus(SC_OK);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("statusCode", 302);
            responseBody.put("message", "Redirecting to profile creation page.");
            responseBody.put("redirectUrl", "/public/register_information?userId=" + userId);

            String json = new ObjectMapper().writeValueAsString(responseBody);
            res.getWriter().write(json);
            return;
        }

        log.info("로그인 성공 및 토큰 생성");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        String accessToken = tokenProvider.createAccessToken(username);
        String refreshToken = UUID.randomUUID().toString();

        res.setHeader(TokenProvider.ACCESS_TOKEN_HEADER, accessToken);
        tokenProvider.saveRefreshTokenToCookie(refreshToken, res);
        refreshTokenService.save(username, refreshToken);
        res.setStatus(SC_OK);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("accessToken", accessToken);
        responseBody.put("message", "로그인 성공");
        responseBody.put("userId", userId);

        String json = new ObjectMapper().writeValueAsString(responseBody);
        res.getWriter().write(json);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res,
            AuthenticationException failed) throws IOException {
        log.error("로그인 실패 : {}", failed.getMessage());

        String errorMessage = "로그인 실패: 자격 증명에 실패하였습니다.";

        if (failed instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호를 확인해주세요.";
        }

        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");
        String json = new ObjectMapper().writeValueAsString(
                new UnauthenticatedResponse(errorMessage)
        );
        res.getWriter().write(json);
    }

}