package com.pallow.pallow.domain.auth.controller;

import com.pallow.pallow.domain.auth.dto.AuthRequestDto;
import com.pallow.pallow.domain.auth.dto.EmailCodeRequestDto;
import com.pallow.pallow.domain.auth.dto.EmailInputRequestDto;
import com.pallow.pallow.domain.auth.dto.LoginRequestDto;
import com.pallow.pallow.domain.auth.service.AuthService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // 로컬 로그인
    @PostMapping("/local")
    public ResponseEntity<CommonResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //응답할때, 토큰을 주기위해 매개변수로 HttpServletResponse 를 사용
        authService.login(loginRequestDto, response);
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_LOGIN_SUCCESS, loginRequestDto.getUsername()));
    }

    // 리프레쉬 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<CommonResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {
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

    // Email 전송
    @PostMapping("/email/send")
    public ResponseEntity<CommonResponseDto> sendVerificationEmail(@Valid @RequestBody EmailInputRequestDto emailInputRequestDto) {
        String code = authService.sendMail(emailInputRequestDto);
        return ResponseEntity.ok(new CommonResponseDto(Message.MAIL_SEND_SUCCESS, code));
        // 임시로 code 를 Postman 을 통해서 확인하기 위해 인증 코드를 반환합니다. 추후 삭제 예정
    }


    @PostMapping("/email/verify")
    public ResponseEntity<CommonResponseDto> verificationEmailCode(@Valid @RequestBody EmailCodeRequestDto emailCodeRequestDto) {
        String checkEmail = authService.verifyCode(emailCodeRequestDto);
        return ResponseEntity.ok(new CommonResponseDto(Message.MAIL_VERIFICATION_CODE_SUCCESS, checkEmail));
    }// True 혹은 False 를 반환 하게끔 만들어서 그걸 토대로 확인 후 다음 페이지로 접근하기 위한 문자열을 응답합니다.
    // Email 확인

    //TODO :  이메일 인증 로직
    // 회원가입시 정보를 입력하고 이메일 발송후 인증 메일의 인증번호와 프론트에서 입력해서 넘겨주는 값이 일치할 경우
    // 회원가입 완료 버튼을 클릭할 수 있다.

    //카카오 소셜 로그인
    //카카오 소셜 회원가입
    //카카오 콜백

    //로그아웃

}


