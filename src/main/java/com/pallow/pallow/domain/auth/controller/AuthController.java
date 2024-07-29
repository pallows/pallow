package com.pallow.pallow.domain.auth.controller;

import com.pallow.pallow.domain.auth.dto.AuthRequestDto;
import com.pallow.pallow.domain.email.dto.EmailCodeRequestDto;
import com.pallow.pallow.domain.email.dto.EmailInputRequestDto;
import com.pallow.pallow.domain.auth.dto.LoginRequestDto;
import com.pallow.pallow.domain.auth.service.AuthService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 유지영 수정
     *
     * @Valid 추가
     */
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signUp(@Valid @RequestBody AuthRequestDto requestDto) {
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_LOCAL_SIGNUP_SUCCESS,
                authService.signUp(requestDto)));
    }

    /**
     * 유지영 수정
     *
     * @Valid 추가
     */
    // 로컬 로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response,
            HttpSession session) {
        try {
            authService.login(loginRequestDto, response);
            return ResponseEntity.ok(new CommonResponseDto(Message.USER_LOGIN_SUCCESS,
                    loginRequestDto.getUsername()));
        } catch (Exception e) {
            session.setAttribute("error", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new CommonResponseDto(Message.USER_LOGIN_FAIL, null));
        }
    }

    // 리프레쉬 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<CommonResponseDto> refreshToken(HttpServletRequest request,
            HttpServletResponse response) {
        authService.tokenReIssue(request, response);
        String RefreshToken = response.getHeader(JwtProvider.REFRESH_HEADER);
        return ResponseEntity.ok(new CommonResponseDto(Message.TOKEN_CREATE_REFRESH, RefreshToken));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<CommonResponseDto> userLogout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_LOGOUT_SUCCESS));
    }

    //카카오 소셜 로그인

    /**
     * 카카오 로그인 엔드포인트 (아직 구현되지 않음)
     * @GetMapping("/kakao")
     *     public ResponseEntity<CommonResponseDto> kakaoLogin() {
     *         // 카카오 로그인 로직 구현 필요
     *         return ResponseEntity.ok(new CommonResponseDto(Message.KAKAO_LOGIN_NOT_IMPLEMENTED));
     *
     */
    //

    //카카오 소셜 회원가입
    //카카오 콜백

    //로그아웃

}


