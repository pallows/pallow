package com.pallow.pallow.domain.auth.controller;

import com.pallow.pallow.domain.auth.dto.AuthRequestDto;
import com.pallow.pallow.domain.auth.dto.LoginRequestDto;
import com.pallow.pallow.domain.auth.service.AuthService;
import com.pallow.pallow.domain.auth.service.OauthService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final OauthService oauthService;
    private final JwtProvider jwtProvider;

    /**
     * 유지영 수정 @Valid 추가
     */
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signUp(@Valid @RequestBody AuthRequestDto requestDto) {
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_LOCAL_SIGNUP_SUCCESS,
                authService.signUp(requestDto)));
    }

    /**
     * 유지영 수정 @Valid 추가
     */
    // 로컬 로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(new CommonResponseDto(Message.USER_LOGIN_SUCCESS, authService.login(loginRequestDto, response)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new CommonResponseDto(Message.USER_LOGIN_FAIL, "Invalid username or password"));
        }
    }

    // 리프레쉬 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<CommonResponseDto> refreshToken(HttpServletRequest request,
                                                          HttpServletResponse response) {
        String newAccessToken = authService.tokenReIssue(request, response);

        log.info("리프레시 토큰 재생성 : {}", newAccessToken);
        return ResponseEntity.ok(new CommonResponseDto(Message.TOKEN_CREATE_REFRESH, newAccessToken));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<CommonResponseDto> userLogout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_LOGOUT_SUCCESS));
    }

    //  소셜 로그인 엔드포인트
    @GetMapping("/oauth/login/{provider}")
    public void oauthLogin(@PathVariable String provider, HttpServletResponse response) {
        log.info("Kakao OAuth login requested");
        oauthService.redirectToProvider(provider, response);
    }

    // 소셜 로그인 콜벡
    @GetMapping("/oauth/callback")
    public void oauthCallback(@RequestParam String code, HttpServletResponse response) throws IOException {
        String token = oauthService.login("kakao", code, response);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(new CommonResponseDto(Message.USER_LOGIN_SUCCESS, token).toString());
    }
//    @PostMapping("/oauth/signup")
//    public ResponseEntity<CommonResponseDto> oauthSignUp(@Valid @RequestBody AuthRequestDto requestDto, HttpServletResponse response) {
//        String token = oauthService.oauthSignUp(requestDto, response);
//        return ResponseEntity.ok(new CommonResponseDto(Message.USER_OAUTH_SIGNUP_SUCCESS, token));
//    }

}
