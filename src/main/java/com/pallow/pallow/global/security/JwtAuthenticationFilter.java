package com.pallow.pallow.global.security;

import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j(topic = "Token Verification Filter")
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    //usename password filter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String token = jwtProvider.getJwtFromHeader(request, JwtProvider.ACCESS_HEADER);
        //서블릿 요청 헤더에서 토큰 가져오기
        if (StringUtils.hasText(token)) { // 토큰이 있는지 확인
            jwtProvider.checkJwtToken(token);// 유효성 검사
            String username = jwtProvider.getUserNameFromJwtToken(token);
            // 토큰에 주제로(Subject)로 넣어줬던 유저네임을 변수에 할당
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // 위의 유저네임을 DB 에서 조회하여 userDetails 라는 변수에 할당 (DB 조회가 가능한지 여부를 판단)
// 여기서 토큰을 계속 생성해준다 매번 - 검증하는 로직만 있으면 될거같다. 아랫것들이 왜 필요한지
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}

