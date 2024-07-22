package com.pallow.pallow.domain.meets.controller;

import com.pallow.pallow.domain.meets.dto.MeetsRequestDto;
import com.pallow.pallow.domain.meets.dto.MeetsResponseDto;
import com.pallow.pallow.domain.meets.service.MeetsService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meets")
public class MeetsController {

    private final MeetsService meetsService;

    @PostMapping("/{user_id}")
    public ResponseEntity<CommonResponseDto> createMeets(@PathVariable Long user_id,
            @RequestBody MeetsRequestDto requestDto) {
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_CREATE_SUCCESS, meetsService.create(user_id, requestDto)));
    }

    @GetMapping("/{meets_id}")
    public ResponseEntity<CommonResponseDto> getMeets(@PathVariable Long meets_id) {
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_READ_SUCCESS, meetsService.getMeets(meets_id)));
    }

    @GetMapping()
    public ResponseEntity<CommonResponseDto> getAllMeets() {
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_READ_SUCCESS, meetsService.getAllMeets()));
    }

    @PatchMapping("/{meets_id}")
    public ResponseEntity<CommonResponseDto> updateMeets(@PathVariable Long meets_id, @RequestBody MeetsRequestDto requestDto) {
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_UPDATE_SUCCESS, meetsService.update(meets_id, requestDto))
        );
    }

    @DeleteMapping("/{meets_id}")
    public ResponseEntity<CommonResponseDto> deleteMeets(@PathVariable Long meets_id) {
        meetsService.delete(meets_id);
        return ResponseEntity.ok(
                new CommonResponseDto(Message.MEET_DELETE_SUCCESS)
        );
    }
}
