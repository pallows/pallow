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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private static final String senderEmail = "pallow-company@gmail.com";
    private final RedisTemplate<String, Object> redisTemplate;

    @Async
    public void sendMail(EmailInputRequestDto emailInputRequestDto) {
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
    }

    private String generateVerificationCode() {
        Random randomCode = new Random();
        int code = 100000 + randomCode.nextInt(900000);
        return String.valueOf(code);
    }

    public String verifyCode(EmailCodeRequestDto emailCodeRequestDto) {
        ValueOperations<String, Object> emailAndCode = redisTemplate.opsForValue();
        if ((emailAndCode.get(emailCodeRequestDto.getEmail())) instanceof String) {
            String storedCode = (String) emailAndCode.get(emailCodeRequestDto.getEmail());
            if (storedCode != null && storedCode.equals(emailCodeRequestDto.getCode())) {
                return "True";
            }
        }
        throw new CustomException(ErrorType.MAIL_MISMATCH_OR_CODE_FORBIDDEN);
    }
}