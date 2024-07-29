package com.pallow.pallow.global.common;

import com.pallow.pallow.domain.meets.dto.MeetsRequestDto;
import com.pallow.pallow.domain.meets.service.MeetsService;
import com.pallow.pallow.domain.meetsreview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CommonController {

    private final MeetsService meetsService;
    private final ReviewService reviewService;

    @GetMapping("/")
    public String home() {return "login";}

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/main.html")
    public String mainPageMove() {
        return "main";
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

    @GetMapping("/groups/{groupId}/invitation")
    public String getInvitationList(@PathVariable("groupId") long groupId, Model model) {return "InvitationList";}

    /**
     * 모임 상세 조회
     */
    @GetMapping("/meets/{meets_id}")
    public String getMeets(@PathVariable Long meets_id, Model model) {
        model.addAttribute("event", meetsService.getMeets(meets_id));
        model.addAttribute("reviews", reviewService.getAllReview(meets_id));
        return "meets";
    }

    /**
     * 모임 전체 조회
     */
    @GetMapping("/meets")
    public String getAllMeets(Model model) {
        model.addAttribute("meets", meetsService.getAllMeets());
        return "meetsCollection";
    }

    /**
     * meets.html에서 모든 리뷰 조회
     */
    @GetMapping("/meets/{meets_id}/review")
    public String getReviews(@PathVariable Long meets_id, Model model) {
        return "meets";
    }

    /**
     * 모임 생성 메서드
     * @param model
     * @return
     */
    @GetMapping("/meets/create")
    public String createMeetsPage(Model model) {
        model.addAttribute("event", new MeetsRequestDto());
        return "meets";
    }

}

/** 유지영 수정 설명 (meets)
 * 여기서는 return 값으로 뷰 이름만 반환합니다. 데이터는 JavaScript에서 AJAX로 불러올 수 있습니다.
 * meets부분은 뷰와 ajax부분을 구분하기 위해 /api/를 @restcontroller부분에만 requestmapping으로 추가해뒀습니다.
 */

