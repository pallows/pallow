package com.pallow.pallow.domain.auth.controller;

import com.pallow.pallow.domain.auth.dto.AuthRequestDto;
import com.pallow.pallow.domain.auth.dto.AuthResponseDto;
import com.pallow.pallow.domain.auth.dto.LoginRequestDto;
import com.pallow.pallow.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    // 회원 가입 1차 작성
    @PostMapping("/local/signup")
    public AuthResponseDto signUp(@RequestBody AuthRequestDto requestDto) {
        return authService.signUp(requestDto);
    }

    // 로그인
    @PostMapping("/local")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //응답할때, 토큰을 주기위해 매개변수로 HttpServletResponse 를 사용
        authService.login(loginRequestDto, response);
        return "로그인완료";
    }

    //카카오 소셜 로그인
    //카카오 소셜 회원가입
    //카카오 콜백


    //토큰 재발행은 보호뙨 리소스에 접근할 때 자동으로 재발급 해주는것으로 생각해보겠습니다.
}


