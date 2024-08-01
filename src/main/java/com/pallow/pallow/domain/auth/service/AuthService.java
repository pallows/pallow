package com.pallow.pallow.domain.auth.service;


import com.pallow.pallow.domain.auth.dto.AuthRequestDto;
import com.pallow.pallow.domain.auth.dto.AuthResponseDto;
import com.pallow.pallow.domain.auth.dto.EmailCodeRequestDto;
import com.pallow.pallow.domain.auth.dto.EmailInputRequestDto;
import com.pallow.pallow.domain.auth.dto.LoginRequestDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.CommonStatus;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Gender;
import com.pallow.pallow.global.enums.Role;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JavaMailSender mailSender;
    private static final String senderEmail = "pallow-company@gmail.com";

    @Transactional
    public AuthResponseDto signUp(AuthRequestDto authRequestDto) {
        if (userRepository.findByUsername(authRequestDto.getUsername()).isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_ACCOUNT_ID);
        }
        Gender gender = Gender.fromString(authRequestDto.getGender());

        User creadtedUser = User.createdUser(
                authRequestDto.getUsername(),
                authRequestDto.getNickname(),
                authRequestDto.getName(),
                authRequestDto.getEmail(),
                passwordEncoder.encode(authRequestDto.getPassword()),
                gender,
                Role.USER);
        userRepository.save(creadtedUser);
        return new AuthResponseDto(creadtedUser.getNickname(), creadtedUser.getEmail());
    }

    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        User user = findByUsername(loginRequestDto.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername()
                        , loginRequestDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        issueTokenAndSave(user, response);
    }

    private void issueTokenAndSave(User user, HttpServletResponse response) {
        String newAccessToken = jwtProvider.createdAccessToken(user.getUsername(),
                user.getUserRole());
        String newRefreshToken = jwtProvider.createdRefreshToken(user.getUsername());
        // Access 토큰을 응답 헤더에 설정
        response.setHeader(JwtProvider.ACCESS_HEADER, newAccessToken);
        response.setHeader(JwtProvider.REFRESH_HEADER, newRefreshToken);
        // Refresh Token 을 Redis 에 저장
        saveRefreshToken(user.getUsername(), newRefreshToken,
                jwtProvider.getJwtRefreshExpiration());
    } // Refresh Token 만료 시간을 가져오기 위해서 JwtProvider 에서 생성자를 생성해서 가져옴

    public void tokenReIssue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtProvider.getJwtFromHeader(request, JwtProvider.REFRESH_HEADER);
        jwtProvider.checkJwtToken(refreshToken); // 토큰 유효성 검사
        String refreshUsername = jwtProvider.getUserNameFromJwtToken(refreshToken);
        User user = findByUsername(refreshUsername);
        String storedRefreshToken = getRefreshToken(refreshUsername);
        log.info("현재있는 토큰 {}", refreshToken);
        log.info("저장된리프레시 토큰 {}", storedRefreshToken);
        String token = storedRefreshToken.substring(7);
        //TODO: 포스트맨에서 확인시 "Bearer " 삭제를 위해 substring 사용, 프론트 구현 후
        // 정상작동 하는지, 확인 요함
        if (!token.equals(refreshToken)) {
            throw new CustomException(ErrorType.TOKEN_MISMATCH);
        }
        issueTokenAndSave(user, response);
    }

    public void logout(HttpServletRequest request) {
        String accessToken = jwtProvider.getJwtFromHeader(request, JwtProvider.ACCESS_HEADER);
        String username = jwtProvider.getUserNameFromJwtToken(accessToken);
        deleteRefreshToken(username);
        SecurityContextHolder.clearContext();
    }

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


    //TODO : N+1 최적화 필요
    // --------------- 해당 클래스에서 사용되어지는 메서드 ---------------
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        // 해당 유저가 있으면 (전 : if 구문에서 예외처리로 확인 -> 수정)
        if (user.getStatus() == CommonStatus.DELETED) { // 유저가 이미 삭제된 유저라면
            throw new CustomException(ErrorType.USER_ALREADY_DELETED);
        }
        return user;
    }

    public void saveRefreshToken(String username, String refreshToken, long duration) {
        redisTemplate.opsForValue().set(username, refreshToken, duration, TimeUnit.MILLISECONDS);
        //username = 키 , refreshToken = 값, duration = 시간, TimeUnit.MILLISECONDS = 단위
    }

    public String getRefreshToken(String username) {
        return (String) redisTemplate.opsForValue().get(username);
    }

    public void deleteRefreshToken(String username) {
        redisTemplate.delete(username);
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


}
