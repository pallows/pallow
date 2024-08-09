package com.pallow.pallow.global.common;

import com.pallow.pallow.domain.meets.service.MeetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class CommonController {

    private final MeetsService meetsService;

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/public/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/public/signup")
    public String signupPage() {
        return "register";
    }

    @GetMapping("/public/chat")
    public String chatPage() {
        return "chat";
    }

    @GetMapping("/public/benefits")
    public String benefitsPage() {
        return "benefits";
    }

    @GetMapping("/public/payments")
    public String paymentsPage() {
        return "payments";
    }

    @GetMapping("/public/register_information")
    public String registerInformationPage() {
        return "register_information";
    }

    @GetMapping("/public/meetsCollection")
    public String meetsCollectionPage(Model model) {
        model.addAttribute("meets", meetsService.getAllMeets());
        return "meetsCollection";
    }

    @GetMapping("/public/userboardCollection")
    public String userboardCollectionPage() {
        return "userboardCollection";
    }

    @GetMapping("/public/MyPage")
    public String MyPage() {
        return "MyPage";
    }

    @GetMapping("/public/userboard")
    public String userboard() {
        return "userboard";
    }

    @GetMapping("/public/kakaoMap")
    public String kakaoMap() {
        return "kakaoMap";
    }
}
