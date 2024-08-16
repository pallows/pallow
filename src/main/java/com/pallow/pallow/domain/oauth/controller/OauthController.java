package com.pallow.pallow.domain.oauth.controller;


import com.pallow.pallow.domain.oauth.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin(origins = {"http://localhost:8080", "https://pallow.net", "https://pallow.net:8080"})
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {
    private final OauthService oauthService;

    //  소셜 로그인 엔드포인트
    @GetMapping("/login/{provider}")
    public void oauthLogin(@PathVariable String provider, HttpServletResponse response) {
        log.info("Kakao OAuth login requested");
        oauthService.redirectToProvider(provider, response);
    }


    @GetMapping("/callbackPage")
    public String oauthCallbackPage() {
        return "callback"; // templates 폴더 내 callback.html 파일을 반환
    }

    // 소셜 로그인 콜벡
    @GetMapping("/callback")
    public ResponseEntity<Map<String, String>> oauthCallback(@RequestParam String code, HttpServletResponse response) {

        Map<String, String> responseMap = oauthService.login("kakao", code, response);
        return ResponseEntity.ok(responseMap);
    }


}
