package com.pallow.pallow.domain.auth.controller;

import com.pallow.pallow.domain.auth.dto.AuthRequestDto;
import com.pallow.pallow.domain.auth.dto.AuthResponseDto;
import com.pallow.pallow.domain.auth.dto.LoginRequestDto;
import com.pallow.pallow.domain.auth.service.AuthService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    // 회원 가입 1차 작성 AuthResponseDto
    @PostMapping("/local/signup")
    public ResponseEntity<CommonResponseDto> signUp(@RequestBody AuthRequestDto requestDto) {
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_LOCAL_SIGNUP_SUCCESS, authService.signUp(requestDto)));
    }

    // 로그인
    @PostMapping("/local")
    public ResponseEntity<CommonResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //응답할때, 토큰을 주기위해 매개변수로 HttpServletResponse 를 사용
        authService.login(loginRequestDto, response);
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_LOGIN_SUCCESS, loginRequestDto.getUsername()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        authService.tokenReIssue(request, response);
        String RefreshToken = response.getHeader(JwtProvider.REFRESH_HEADER);
        return ResponseEntity.ok(new CommonResponseDto(Message.TOKEN_CREATE_REFRESH, RefreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponseDto> userLogout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_LOGOUT_SUCCESS));
    }


    //카카오 소셜 로그인
    //카카오 소셜 회원가입
    //카카오 콜백

    //로그아웃

}


