package com.pallow.pallow.global.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

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

    @GetMapping("/register_information")
    public String registerInformationPage() {
        return "register_information";
    }

}
