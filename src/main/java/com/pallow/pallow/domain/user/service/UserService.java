package com.pallow.pallow.domain.user.service;

import com.pallow.pallow.domain.user.dto.SignupRequestDto;
import com.pallow.pallow.domain.user.dto.UserRequestDto;
import com.pallow.pallow.domain.user.dto.UserResponseDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.RefreshTokenRepository;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.CommonStatus;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Gender;
import com.pallow.pallow.global.enums.Role;
import com.pallow.pallow.global.exception.CustomException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 이용자 회원가입
     *
     * @param dto 회원가입 시 필요한 정보
     * @return 회원가입된 이용자 엔티티
     */
    @Transactional
    public User signup(SignupRequestDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new CustomException(ErrorType.DUPLICATE_ACCOUNT_ID);
        }

        if (userRepository.findByNickname(dto.getNickname()).isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_ACCOUNT_NICKNAME);
        }
        User creadtedUser = User.createdUser(
                dto.getUsername(),
                dto.getNickname(),
                dto.getEmail(),
                dto.getName(),
                Gender.fromString(dto.getGender()),
                passwordEncoder.encode(dto.getPassword()),
                Role.USER,
                dto.getKakaoId());
        userRepository.save(creadtedUser);
        return creadtedUser;
    }

    /**
     * 이용자 로그아웃
     *
     * @param user 로그아웃 요청한 이용자
     */
    @Transactional
    public void logout(User user) { //TODO 쿠키 유효성 제거
        refreshTokenRepository.deleteById(user.getUsername());
    }


    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
    }

    public void deleteUser(User user) {
        user.softDeleteUser();
        userRepository.save(user);
    }

    public List<UserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(e -> e.getStatus() != CommonStatus.DELETED)
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto updateUser(Long userId, UserRequestDto requestDto) {
        User user = findUserById(userId);
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        user.updateUser(requestDto.getNickname(), encodedPassword);
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public UserResponseDto findById(Long userId) {
        User user = findUserById(userId);
        return new UserResponseDto(user);
    }

}
