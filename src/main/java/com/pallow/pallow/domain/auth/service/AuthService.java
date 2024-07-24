package com.pallow.pallow.domain.auth.service;


import com.pallow.pallow.domain.auth.dto.AuthRequestDto;
import com.pallow.pallow.domain.auth.dto.AuthResponseDto;
import com.pallow.pallow.domain.auth.dto.LoginRequestDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;

import com.pallow.pallow.domain.user.service.UserService;
import com.pallow.pallow.global.enums.Role;
import com.pallow.pallow.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Transactional
    public AuthResponseDto signUp(AuthRequestDto authRequestDto) {
        User user = findByUsername(authRequestDto.getUsername());
        if (user != null) { // 해당 유저가 있으면
            throw new RuntimeException("The user is already registered"); // 예외처리 수정해야합니다.
        }
        Role role = Role.USER;
        User creadtedUser = User.createdUser(authRequestDto.getUsername(),
                authRequestDto.getNickname(),
                authRequestDto.getEmail(),
                passwordEncoder.encode(authRequestDto.getPassword()), role);
        userRepository.save(creadtedUser);
        return new AuthResponseDto(creadtedUser.getId(), creadtedUser.getNickname(), creadtedUser.getEmail());
    }

    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        User user = findByUsername(loginRequestDto.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );
        // authenticationManager 는 authenticate 메서드를 통해
        // UsernamePasswordAuthenticationToken 객체를 받아들이고,
        // 설정된 AuthenticationProvider 들을 사용하여 인증을 시도
        SecurityContextHolder.getContext().setAuthentication(authentication);

        issueTokenAndSave(user, response);
    }

    private void issueTokenAndSave(User user, HttpServletResponse response) {
        String newAccessToken = jwtProvider.createdAccessToken(user.getUsername(), user.getUserRole());
        String newRefreshToken = jwtProvider.createdRefreshToken(user.getUsername());
        response.setHeader(JwtProvider.ACCESS_HEADER, newAccessToken);
    }

    //리프레쉬 토큰 관리 레디스 추가수정
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) { // 해당 유저가 있으면
            User userDeletedAt = user.get(); // Optional 에서 User 객체를 추출
            if (userDeletedAt.getDeletedAt() != null) { // 유저가 이미 삭제된 유저라면
                throw new RuntimeException("The user has been deleted."); // 예외처리 수정해야합니다.
            }
            //    throw new RuntimeException("The user is already registered"); // 예외처리 수정해야합니다.
            return user.get();
        }
        return null; // 유저가 있기 때문에, null 아무것도 행하지 않는다.
    }
}
