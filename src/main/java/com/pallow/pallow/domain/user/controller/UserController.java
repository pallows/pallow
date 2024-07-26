package com.pallow.pallow.domain.user.controller;

import com.pallow.pallow.domain.user.Dto.UserRequestDto;
import com.pallow.pallow.domain.user.Dto.UserResponseDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.service.UserService;
import com.pallow.pallow.global.common.CommonResponseDto;
import com.pallow.pallow.global.enums.CommonStatus;
import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponseDto> deleteUser(@PathVariable Long userId, UserDetailsImpl currentUser) {
        userService.deleteUser(userId, currentUser.getUser().getId()); //본인 계정만 본인이 탈퇴
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_DELETE_SUCCESS));
    }

    // 회원전체조회
    @GetMapping
    public ResponseEntity<CommonResponseDto> findAllUser() {
        List<UserResponseDto> users = userService.findAllUsers();
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_READ_ALL_SUCCESS, users));
    }

    // 회원수정
    @PutMapping("/{userId}")
    public ResponseEntity<CommonResponseDto> updateUser(@PathVariable Long userId,
                                                        @RequestBody UserRequestDto requestDto) {
        UserResponseDto user = userService.updateUser(userId, requestDto);
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_UPDATE_SUCCESS, user));
    }


}
