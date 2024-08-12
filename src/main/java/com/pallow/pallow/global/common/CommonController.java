package com.pallow.pallow.global.common;

import com.pallow.pallow.domain.meets.service.MeetsService;
import com.pallow.pallow.domain.userboard.dto.UserBoardResponseDto;
import com.pallow.pallow.domain.userboard.service.UserBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class CommonController {

    private final MeetsService meetsService;
    private final UserBoardService userBoardService;

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/public/main")
    public String mainPage(Model model) {
        model.addAttribute("meets", meetsService.getAllMeets());
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

    @GetMapping("/public/meets/{meetsId}")
    public String meetsPage() {
        return "meets";
    }

    @GetMapping("/public/meetsCollection")
    public String meetsCollectionPage(Model model) {
        model.addAttribute("meets", meetsService.getAllMeets());
        return "meetsCollection";
    }

    @GetMapping("/public/profileCollection")
    public String profileCollectionPage() {
        return "profileCollection";
    }

    @GetMapping("/public/userboardCollection")
    public String getUserBoardCollection(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int size) {
        Page<UserBoardResponseDto> profiles = userBoardService.getUserBoardsPage(page, size);
        model.addAttribute("profiles", profiles);
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

    @GetMapping("/public/popularMeets")
    public String popularMeets() { return "popularMeets"; }

    @GetMapping("/public/kakaoMap")
    public String kakaoMap() {
        return "kakaoMap";
    }
}
