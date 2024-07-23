package com.pallow.pallow.domain.auth.service;


import com.pallow.pallow.domain.auth.dto.AuthRequestDto;
import com.pallow.pallow.domain.auth.dto.AuthResponseDto;
import com.pallow.pallow.domain.auth.dto.LoginRequestDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;

import com.pallow.pallow.global.enums.Role;
import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthResponseDto signUp(AuthRequestDto authRequestDto) {
        Optional<User> user = userRepository.findByUsername(authRequestDto.getUsername());
        if (user.isPresent()) { // 해당 유저가 있으면
            User userDeletedAt = user.get(); // Optional 에서 User 객체를 추출
            if (userDeletedAt.getDeletedAt() != null) { // 유저가 이미 삭제된 유저라면
                throw new RuntimeException("The user has been deleted."); // 예외처리 수정해야합니다.
            }
            throw new RuntimeException("The user is already registered"); // 예외처리 수정해야합니다.
        }
        Role role = Role.USER;
        User creadtedUser = User.createdUser(authRequestDto.getUsername(),
                authRequestDto.getNickname(),
                authRequestDto.getEmail(),
                authRequestDto.getPassword(), role);
        userRepository.save(creadtedUser);
        return new AuthResponseDto(creadtedUser.getId(), creadtedUser.getNickname(), creadtedUser.getEmail());
    }

    public AuthResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return null;
    }
}
