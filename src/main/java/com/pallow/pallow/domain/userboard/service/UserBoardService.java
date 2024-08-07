package com.pallow.pallow.domain.userboard.service;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.domain.userboard.dto.UserBoardRequestDto;
import com.pallow.pallow.domain.userboard.dto.UserBoardResponseDto;
import com.pallow.pallow.domain.userboard.entity.UserBoard;
import com.pallow.pallow.domain.userboard.repository.UserBoardRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBoardService {

    private final UserBoardRepository userBoardRepository;
    private final UserRepository userRepository;

    public UserBoardResponseDto createBoard(UserBoardRequestDto requestDto, User user,
            long userId) {
        User createdBy = userRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        if (isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }
        UserBoard userBoard = userBoardRepository.save(requestDto.toEntity(createdBy));
        return new UserBoardResponseDto(userBoard);
    }

    public UserBoardResponseDto getBoard(long userId, long userBoardId, User user) {
        UserBoard userBoard = userBoardRepository.findById(userBoardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_BOARD));
        return new UserBoardResponseDto(userBoard);
    }

    public List<UserBoardResponseDto> getBoards(long userId, User user) {
        List<UserBoard> userBoards = userBoardRepository.findAllByUserId(userId);
        return userBoards.stream().map(UserBoardResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public UserBoardResponseDto updateUserBoard(long userId, long userBoardId,
            UserBoardRequestDto requestDto, User user) {
        UserBoard userBoard = userBoardRepository.findById(userBoardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_BOARD));
        if (isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }
        userBoard.update(requestDto);
        return new UserBoardResponseDto(userBoard);
    }

    @Transactional
    public void deleteUserBoard(long userId, long userBoardId, User user) {
        UserBoard userBoard = userBoardRepository.findById(userBoardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_BOARD));
        if (isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }
        userBoardRepository.delete(userBoard);
    }

    private boolean isSameIdAndUser(Long userId, User user) {
        return !user.getId().equals(userId);
    }
}
