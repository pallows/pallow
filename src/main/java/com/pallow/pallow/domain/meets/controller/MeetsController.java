package com.pallow.pallow.domain.meets.controller;

import com.pallow.pallow.domain.meets.dto.MeetsRequestDto;
import com.pallow.pallow.domain.meets.service.MeetsService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API 경로를 구분하기 위해 @Requestmapping에 "/api" 접두사 추가
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meets")
public class MeetsController {

    private final MeetsService meetsService;

    /**
     * 모임 생성
     *
     * @param requestDto  생성 데이터 [title, content]
     * @param userDetails 유저 데이터
     * @return 생성 성공 메시지 + 생성된 리뷰 데이터
     */
    @PostMapping()
    public ResponseEntity<CommonResponseDto> createMeets(@ModelAttribute @Valid MeetsRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info(String.valueOf(userDetails.getUser().getId()));
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_CREATE_SUCCESS,
                        meetsService.create(requestDto, userDetails.getUser())));
    }

    /**
     * 모임 선택 조회
     *
     * @param meets_id 그룹 ID
     * @return 생성 성공 메시지 + 그룹 데이터
     */
    @GetMapping("/{meets_id}")
    public ResponseEntity<CommonResponseDto> getMeets(@PathVariable Long meets_id) {
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_READ_SUCCESS, meetsService.getMeets(meets_id)));
    }

    /**
     * 모임 전체 조회
     *
     * @return 생성 성공 메시지 + 그룹 데이터
     */
    @GetMapping()
    public ResponseEntity<CommonResponseDto> getAllMeets() {
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_READ_SUCCESS, meetsService.getAllMeets()));
    }

    /**
     * 모임 업데이트
     *
     * @param meets_id    그룹 ID
     * @param requestDto  변경할 데이터 [title, content]
     * @param userDetails 유저 데이터
     * @return 업데이트 성공 메시지 + 변경된 리뷰 데이터
     */
    @PatchMapping("/{meets_id}")
    public ResponseEntity<CommonResponseDto> updateMeets(@PathVariable Long meets_id,
                                                         @ModelAttribute @Valid MeetsRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_UPDATE_SUCCESS,
                        meetsService.update(meets_id, requestDto, userDetails.getUser()))
        );
    }

    /**
     * 모임 삭제
     *
     * @param meets_id    그룹 ID
     * @param userDetails 유저 데이터
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/{meets_id}")
    public ResponseEntity<CommonResponseDto> deleteMeets(@PathVariable Long meets_id,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetsService.delete(meets_id, userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_DELETE_SUCCESS)
        );
    }

    /**
     * 그룹 맴버 조회
     *
     * @param meets_id
     * @return List<UserResponseDto>
     */
    @GetMapping("/{meets_id}/memberList")
    public ResponseEntity<CommonResponseDto> getAllMeetsMembers(@PathVariable Long meets_id) {
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_MEMBER_READ_SUCCESS,
                        meetsService.getAllMeetsMembers(meets_id))
        );
    }

    /**
     * 회원 강퇴
     *
     * @param meets_id
     * @param user_id
     * @param userDetails
     * @return success message
     */
    @GetMapping("/{meets_id}/withdraw/{user_id}")
    public ResponseEntity<CommonResponseDto> withdrawMember(@PathVariable Long meets_id,
                                                            @PathVariable Long user_id,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetsService.withdrawMember(meets_id, user_id, userDetails.getUser());
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_WITHDRAW_MEMBER_SUCCESS)
        );
    }

    /**
     * 좋아요 토글
     *
     * @param meets_id
     * @param userDetails
     * @return
     */
    @PostMapping("{meets_id}/like")
    public ResponseEntity<CommonResponseDto> likeReview(@PathVariable Long meets_id,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        meetsService.toggleLike(meets_id, userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.LIKES_TOGGLE_SUCCESS));
    }
}
