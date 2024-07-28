package com.pallow.pallow.global.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommonController {

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/login.html")
    public String loginPage1() {
        return "login";
    }

    @GetMapping("/auth/local/signup")
    public String signupPage() {
        return "register";
    }

    @GetMapping("/auth/local/register_information.html")
    public String registerInformationPage() {
        return "register_information";
    }

}
