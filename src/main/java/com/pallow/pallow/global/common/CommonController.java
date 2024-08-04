package com.pallow.pallow.global.common;

import com.pallow.pallow.domain.meets.service.MeetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
public class CommonController {

    private final MeetsService meetsService;

    @GetMapping("/")
    public String home() {return "login";}

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "register";
    }

    @GetMapping("/chat")
    public String chatPage() {
        return "chat";
    }

    @GetMapping("/benefits")
    public String benefitsPage()  {
        return "benefits";
    }

    @GetMapping("/payments")
    public String paymentsPage()  {
        return "payments";
    }

    @GetMapping("/register_information")
    public String registerInformationPage() {
        return "register_information";
    }

    @GetMapping("/meetsCollection")
    public String meetsCollectionPage(Model model) {
        model.addAttribute("meets", meetsService.getAllMeets());
        return "meetsCollection";
    }
}
