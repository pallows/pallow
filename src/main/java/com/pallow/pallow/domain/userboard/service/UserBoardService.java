package com.pallow.pallow.domain.userboard.service;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.domain.user.service.UserService;
import com.pallow.pallow.domain.userboard.dto.UserBoardRequestDto;
import com.pallow.pallow.domain.userboard.dto.UserBoardResponseDto;
import com.pallow.pallow.domain.userboard.entity.UserBoard;
import com.pallow.pallow.domain.userboard.repository.UserBoardRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBoardService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserBoardRepository userBoardRepository;

    public UserBoardResponseDto createBoard(UserBoardRequestDto requestDto, User user,
            long userId) {
        User createdBy = userService.findUserById(user.getId());
        UserBoard userBoard = userBoardRepository.save(requestDto.toEntity(createdBy));
        return new UserBoardResponseDto(userBoard);
    }

    public UserBoardResponseDto getBoard(long userId, long userBoardId, User user) {
        UserBoard userBoard = userBoardRepository.findById(userBoardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_BOARD_ID));
        return new UserBoardResponseDto(userBoard);
    }

    public List<UserBoardResponseDto> getBoards(long userId, User user) {
        List<UserBoard> userBoards = userBoardRepository.findAllById(userId);
        return userBoards.stream().map(UserBoardResponseDto::new).toList();
    }

    public UserBoardResponseDto updateUserBoard(long userId, long userBoardId, UserBoardRequestDto requestDto, User user) {
        UserBoard userBoard = userBoardRepository.findById(userBoardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_BOARD_ID));
        userBoard.update(requestDto);
        return new UserBoardResponseDto(userBoard);
    }
}
