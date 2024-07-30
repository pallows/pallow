package com.pallow.pallow.domain.user.service;

import com.pallow.pallow.domain.user.Dto.UserRequestDto;

import com.pallow.pallow.domain.user.Dto.UserResponseDto;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.CommonStatus;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
    }


    public void deleteUser(Long userId, Long currentUserId) {
        if (!Objects.equals(userId, currentUserId)) { // 탈퇴를 하기위한 유저가 본인인지 검사
            throw new CustomException(ErrorType.NOT_AUTHORIZED_TO_DELETE_ACCOUNT);
        }
        User user = findUserById(userId);
        user.softDeleteUser();
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
        user.updateUser(requestDto.getNickname(), encodedPassword, requestDto.getPosition());
        userRepository.save(user);
        return new UserResponseDto(user);
    }


}
