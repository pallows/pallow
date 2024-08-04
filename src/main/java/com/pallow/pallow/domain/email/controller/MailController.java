package com.pallow.pallow.domain.email.controller;

import com.pallow.pallow.domain.email.dto.EmailCodeRequestDto;
import com.pallow.pallow.domain.email.dto.EmailInputRequestDto;
import com.pallow.pallow.domain.email.service.MailService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<CommonResponseDto> sendVerificationEmail(@Valid @RequestBody EmailInputRequestDto emailInputRequestDto) {
        mailService.sendMail(emailInputRequestDto);
        return ResponseEntity.ok(new CommonResponseDto(Message.MAIL_SEND_SUCCESS));
    }

    @PostMapping("/verify")
    public ResponseEntity<CommonResponseDto> verificationEmailCode(@Valid @RequestBody EmailCodeRequestDto emailCodeRequestDto) {
        String checkEmail = mailService.verifyCode(emailCodeRequestDto);
        return ResponseEntity.ok(new CommonResponseDto(Message.MAIL_VERIFICATION_CODE_SUCCESS, checkEmail));
    }

}
