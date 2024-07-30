package com.pallow.pallow.global.jwt;


import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.enums.Role;
import com.pallow.pallow.global.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    public static final String ACCESS_HEADER = "Authorization";

    public static final String REFRESH_HEADER = "X-Refresh-Token";

    public static final String AUTHORIZATION_KEY = "auth";

    public static final String BEARER_PREFIX = "Bearer ";


    @Value("${jwt.secret.key}") // Lombok 을 불러오면 안됌
    // Jwt 아래 secret 이래 key 를 불러오기 때문에, 위와같은 형식으로 적어야한다.
    private String jwtSecret;
    private Key key;
    // Key 는 JWT 라이브러리에서 사용되는 암호화 키 서명을 생성하고 검증 Key 객체 사용

    @Value("${jwt.access.expiration}")
    private int jwtAccessExpiration;
    // millisecond 단위
    // Access 토큰 유효 시간

    @Value("${jwt.refresh.expiration}")
    private int jwtRefreshExpiration;
    // Refresh 토큰 유효 시간

    public long getJwtRefreshExpiration() {
        return jwtRefreshExpiration;
    }

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
    // Json Web Token 라이브러리에서 사용되는 클래스 혹은 열거형 enum, JWT 를 서명하고 검증할 때 사용하는 알고리즘을 정의

    @PostConstruct
    //빈이 초기화된 후에 실행할 메서드를 지정할 때 사용
    public void initKey() {
        // Base64 디코딩
        byte[] bytes = Base64.getDecoder().decode(jwtSecret);
        // Base64 바이너리 데이터를 텍스트 형식으로 인코딩하기 위한 클래스
        key = Keys.hmacShaKeyFor(bytes);
        // Keys 클래스와 hmacShaKeyFor 메서드는 jjwt (Java JWT)
        // 라이브러리에서 제공하는 도구로, JWT 서명 및 검증에 필요한 키를 생성하는 데 사용
        // Keys 다양한 유형의 암호화 키를 생성하기 위한 유틸리티 메서드를 제공하는 클래스
        // hmacShaKeyFor HMAC (Hash-based Message Authentication Code) SHA 알고리즘에 사용할 수 있는 비밀 키를 생성
        // HMAC 메시지의 무결성과 인증을 검증하기 위해 사용되는 해쉬 기반 알고리즘
    }

    // JWT 토큰 생성
    public String createdAccessToken(String username, Role userRole) {
        Date date = new Date();
        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username) // 토큰의 주제를 설정
                .claim(AUTHORIZATION_KEY, userRole)
                // 클레임은 토큰에 유용한 정보를 포함시켜, 서버가 토큰을 검증할 때 해당 정보를 활용
                // claim 은 JWT (Json Web Token)에서 사용되는 개념으로, 토큰에 포함된 정보의 한 조각을 의미
                .setIssuedAt(date) // 발행시간
                .setExpiration(new Date((date.getTime() + jwtAccessExpiration))) // 만료시간
                .signWith(key, signatureAlgorithm) // 서명 설정
                .compact(); // JWT 토큰 생성
    }

    // Refresh 토큰 생성
    public String createdRefreshToken(String username) {
        Date date = new Date();
        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username)
                .setIssuedAt(date)
                .setExpiration(new Date((date.getTime() + jwtRefreshExpiration)))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request, String headerName) {
        System.out.println("요청 받은 URL : " + request.getRequestURL());
        String token = request.getHeader(headerName);

        // TODO: 각각 엔드포인트에 대한 개별 설정 수정 요망.. ㅠㅠ
        //String requestUrl = request.getRequestURI();
        // if (!request.getMethod().equals("GET") // 모든 요청에 대해서 필터를 돌지만  GET 이 아니면서
        //        && !(requestUrl.equals("http://localhost:8080/auth/local") // local 로그인이 아니거나
        //        || requestUrl.equals("http://localhost:8080/auth/signup") // local 회원 가입이 아니거나
        //        || requestUrl.equals("http://localhost:8080/email/send") // 이메일 전송이 아니거나
        //        || requestUrl.equals("http://localhost:8080/email/verify"))// 이메일 인증이 아닐때
        //        && (!StringUtils.hasText(token) // 면서 토큰이 비었거나
        //        || !token.startsWith(BEARER_PREFIX))) //Bearer 로 시작하지 않으면
        // { //카카오 로그인일 경우에도 토큰 제외 해야함
        //    throw new CustomException(ErrorType.TOKEN_CHECK_INVALID); // 이 토큰은 잘못되었다고 예외를 던진다.
        // }
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        // ->> 필터
        return null;
        // RuntimeException 수정 요망 **
    }

    // JWT 토큰에서 사용자 이름 추출
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                // JWT parserBuilder 를 생성합니다. 이 빌더는 JWT 를 파싱하는 데 사용
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody() // JWT 의 페이로드를 반환
                .getSubject();
    }

    // JWT 유효성 검증
    public void checkJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new CustomException(ErrorType.TOKEN_CHECK_INVALID);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new CustomException(ErrorType.TOKEN_CHECK_INVALID);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new CustomException(ErrorType.TOKEN_CHECK_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new CustomException(ErrorType.TOKEN_CHECK_INVALID);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new CustomException(ErrorType.TOKEN_CHECK_INVALID);
        }
    }
}
