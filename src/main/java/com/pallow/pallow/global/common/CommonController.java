package com.pallow.pallow.global.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/main.html")
    public String mainPageMove() {
        return "main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/login.html")
    public String loginPageMove() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "register";
    }

    @GetMapping("/register_information")
    public String registerInformationPage() {
        return "register_information";
    }

}
