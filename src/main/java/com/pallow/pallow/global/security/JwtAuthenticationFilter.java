package com.pallow.pallow.global.security;

import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.pallow.pallow.global.jwt.JwtProvider.BEARER_PREFIX;


@Slf4j(topic = "Token Verification Filter")
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

//    @Override // TODO 임시 모든 필터 비활성화
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String[] excludePaths = {
//                "/auth/signup",
//                "/auth/login",
//                "/api/email/**",
//                "/register_information",
//                "/signup",
//                "/auth/refresh",
//                "/js/**",
//                "/static/images/**",
//                "/**/css/*.css",
//                "/static/css/**",
//                //  "/static/html/**",
//                //  "/**/*.html"
//                "/register_information" // 임시
//                , "/main.html"//
//                , "/login.html"//
//        };
//        String path = request.getRequestURI();
//        for (String excludePath : excludePaths) {
//            if (pathMatcher.match(excludePath, path)) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 모든 경로에 대해 필터 비활성화
        return true;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String token = jwtProvider.getJwtFromHeader(request, JwtProvider.ACCESS_HEADER);


        //서블릿 요청 헤더에서 토큰 가져오기
        log.info("Filter, JWT Token: {}", token);
        log.info("JwtProvider.ACCESS_HEADER: {}", request.getHeader(JwtProvider.ACCESS_HEADER));
        if (StringUtils.hasText(token)) { // 토큰이 있는지 확인
            jwtProvider.checkJwtToken(token);// 유효성 검사
            String username = jwtProvider.getUserNameFromJwtToken(token);
            // 토큰에 주제로(Subject)로 넣어줬던 유저네임을 변수에 할당
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // 위의 유저네임을 DB 에서 조회하여 userDetails 라는 변수에 할당 (DB 조회가 가능한지 여부를 판단)
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("필터 성공적 통과");
        } else {
            log.info("로그인을 필요로 합니다.");
            // 에외를 던져서 시큐리티 설정에서 처리함
            throw new ServletException("Need Login");
        }

        filterChain.doFilter(request, response);
    }
}

