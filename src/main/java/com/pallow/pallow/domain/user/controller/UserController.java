package com.pallow.pallow.domain.user.controller;


import com.pallow.pallow.domain.user.dto.LoginRequestDto;
import com.pallow.pallow.domain.user.dto.SignupRequestDto;
import com.pallow.pallow.domain.user.dto.UserRequestDto;
import com.pallow.pallow.domain.user.dto.UserResponseDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.service.UserService;
import com.pallow.pallow.global.common.CommonResponseDto;

import com.pallow.pallow.global.enums.Message;
import com.pallow.pallow.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(
            @Valid @RequestBody SignupRequestDto dto) {
        return ResponseEntity.ok(
                new CommonResponseDto(Message.USER_LOCAL_SIGNUP_SUCCESS, userService.signup(dto)));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponseDto> logout(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.logout(userDetails.getUser());
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_LOGOUT_SUCCESS));
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponseDto> deleteUser(@PathVariable Long userId,
            UserDetailsImpl currentUser) {
        userService.deleteUser(userId, currentUser.getUser().getId()); //본인 계정만 본인이 탈퇴
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_DELETE_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto> findAllUser() {
        List<UserResponseDto> users = userService.findAllUsers();
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_READ_ALL_SUCCESS, users));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CommonResponseDto> updateUser(@PathVariable Long userId,
            @RequestBody UserRequestDto requestDto) {
        UserResponseDto user = userService.updateUser(userId, requestDto);
        return ResponseEntity.ok(new CommonResponseDto(Message.USER_UPDATE_SUCCESS, user));
    }


}
