package com.pallow.pallow.domain.email.service;

import com.pallow.pallow.domain.email.dto.EmailCodeRequestDto;
import com.pallow.pallow.domain.email.dto.EmailInputRequestDto;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private static final String senderEmail = "pallow-company@gmail.com";
    private final RedisTemplate<String, Object> redisTemplate;

    public String sendMail(EmailInputRequestDto emailInputRequestDto) {
        String code = generateVerificationCode();
        ValueOperations<String, Object> emailAndCode = redisTemplate.opsForValue();
        emailAndCode.set(emailInputRequestDto.getEmail(), code, 5, TimeUnit.MINUTES);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailInputRequestDto.getEmail());
            helper.setFrom(senderEmail);
            helper.setSubject("Pallow Email 인증 코드입니다.");

            String body = "<html><body>";
            body += "<img src='https://github.com/user-attachments/assets/38c43d3b-dce7-422c-b89a-572308799e96' alt='Pallow logo' />";
            body += "<h3>요청하신 인증 번호입니다.</h3>";
            body += "<h1>" + code + "</h1>";
            body += "</body></html>";
            helper.setText(body, true);

            mailSender.send(message);
        } catch (Exception e) {
            log.error("메일 전송 오류: ", e);
            throw new CustomException(ErrorType.MAIL_MISMATCH_OR_CODE_FORBIDDEN);
        }
        return code;
    }

    private String generateVerificationCode() {
        Random randomCode = new Random(); // Random 클래스 생성 무작위 수를 위한 유틸리티 클래스
        int code = 100000 + randomCode.nextInt(900000);
        // randomCode.nextInt(900000) : 0~899999 사이의 랜덤한 정수를 생성
        // nextInt 는 지정된 범위 내 무작위 숫자 반환
        // 앞에 있는 100000은 생성되는 랜덤 정수에 더하는 수 즉 100000~999999 까지의 랜덤한 정수 생성
        return String.valueOf(code);
        // 그 수를 문자열화 해서 return
    }

    public String verifyCode(EmailCodeRequestDto emailCodeRequestDto) {
        ValueOperations<String, Object> emailAndCode = redisTemplate.opsForValue();
        if ((emailAndCode.get(emailCodeRequestDto.getEmail())) instanceof String) {
            String storedCode = (String) emailAndCode.get(emailCodeRequestDto.getEmail());
            if (storedCode != null && storedCode.equals(emailCodeRequestDto.getCode())) {
                return "True";
            }
        }
        log.info("이메일 인증 실패");
        throw new CustomException(ErrorType.MAIL_MISMATCH_OR_CODE_FORBIDDEN);
    }
}