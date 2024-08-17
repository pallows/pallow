package com.pallow.pallow.global.common;

import com.pallow.pallow.domain.invitedboard.entity.InvitedBoard;
import com.pallow.pallow.domain.invitedboard.repository.InvitedBoardRepository;
import com.pallow.pallow.domain.meets.service.MeetsService;
import com.pallow.pallow.domain.profile.dto.ProfileResponseDto;
import com.pallow.pallow.domain.profile.service.ProfileService;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.domain.userboard.dto.UserBoardResponseDto;
import com.pallow.pallow.domain.userboard.service.UserBoardService;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.model.ModelMutationLogging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class CommonController {

    private final MeetsService meetsService;
    private final UserBoardService userBoardService;
    private final ProfileService profileService;
    private final UserRepository userRepository;
    private final InvitedBoardRepository invitedBoardRepository;

    @Value("${kakao.map.app-key}")
    private String kakaoMapAppKey;

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
    public String registerInformationPage(Model model) {
        model.addAttribute("kakaoMapAppKey", kakaoMapAppKey);
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

    @GetMapping("/public/Profile")
    public String profilePage(@RequestParam("nickname") String nickname, Model model) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        ProfileResponseDto profile = profileService.getProfile(user.getId());
        model.addAttribute("profile", profile);
        model.addAttribute("userId", user.getId());
        return "profile";
    }

    @GetMapping("/public/userboard")
    public String userboard() {
        return "userboard";
    }

    @GetMapping("/public/kakaoMap")
    public String kakaoMap(Model model) {
        model.addAttribute("kakaoMapAppKey", kakaoMapAppKey);
        return "kakaoMap";
    }

    @GetMapping("/public/InvitationList")
    public String InvitationList() {
        return "InvitationList";
    }
}
