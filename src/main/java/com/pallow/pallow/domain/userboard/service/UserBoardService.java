package com.pallow.pallow.domain.userboard.service;

import com.pallow.pallow.domain.like.service.LikeService;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserCustomRepository;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.domain.userboard.dto.UserBoardRequestDto;
import com.pallow.pallow.domain.userboard.dto.UserBoardResponseDto;
import com.pallow.pallow.domain.userboard.entity.UserBoard;
import com.pallow.pallow.domain.userboard.repository.UserBoardCustomRepository;
import com.pallow.pallow.domain.userboard.repository.UserBoardRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import com.pallow.pallow.global.s3.service.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBoardService {

    private final LikeService likeService;
    private final ImageService imageService;
    private final UserBoardRepository userBoardRepository;
    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;
    private final UserBoardCustomRepository userBoardCustomRepository;

    public UserBoardResponseDto createBoard(UserBoardRequestDto requestDto, User user,
            long userId) {
        User createdBy = userCustomRepository.findById(user.getId());
        if (isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }

        // 이미지 업로드
        String imageUrl = null;
        try {
            imageUrl = imageService.imageUpload(requestDto.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserBoard userBoard = userBoardRepository.save(requestDto.toEntity(createdBy, imageUrl));
        return new UserBoardResponseDto(userBoard);
    }

    public UserBoardResponseDto getBoard(long userId, long userBoardId, User user) {
        UserBoard userBoard = userBoardCustomRepository.findById(userBoardId);
        return new UserBoardResponseDto(userBoard);
    }

    public List<UserBoardResponseDto> getBoards(long userId, User user) {
        List<UserBoard> userBoards = userBoardRepository.findAllByUserId(userId);
        return userBoards.stream().map(UserBoardResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public UserBoardResponseDto updateUserBoard(long userId, long userBoardId,
            UserBoardRequestDto requestDto, User user) {
        UserBoard userBoard = userBoardCustomRepository.findById(userBoardId);
        if (isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }

        // 이미지 업로드
        String imageUrl = null;
        if (requestDto.getImage() != null && !requestDto.getImage().isEmpty()) {
            try {
                imageService.deleteImage(userBoard.getImage());
                imageUrl = imageService.imageUpload(requestDto.getImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            imageUrl = userBoard.getImage(); // 기존 이미지 URL 유지
        }

        userBoard.update(requestDto, imageUrl);
        return new UserBoardResponseDto(userBoard);
    }

    @Transactional
    public void deleteUserBoard(long userId, long userBoardId, User user) {
        UserBoard userBoard = userBoardCustomRepository.findById(userBoardId);
        if (isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }

        // 이미지 삭제
        if (userBoard.getImage() != null && !userBoard.getImage().isEmpty()) {
            imageService.deleteImage(userBoard.getImage());
        }

        userBoardRepository.delete(userBoard);
    }

    /**
     * 좋아요 토글
     *
     * @param userBoardId
     * @param user
     */
    @Transactional
    public void toggleLike(Long userBoardId, User user) {
        likeService.toggleLike(userBoardId, user, userBoardRepository);
    }

    private boolean isSameIdAndUser(Long userId, User user) {
        return !user.getId().equals(userId);
    }

    @Transactional(readOnly = true)
    public Page<UserBoardResponseDto> getUserBoardsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<UserBoard> userBoardPage = userBoardRepository.findAll(pageable);
        return userBoardPage.map(UserBoardResponseDto::new);
    }

    /**
     * 오늘의 동친 (무작위 선택)
     */
    @Transactional(readOnly = true)
    public List<UserBoardResponseDto> getTodaysFriends(User user, int limit) {
        List<UserBoard> todaysFriends = userBoardRepository.findRandomByPosition(
                user.getProfile().getPosition(), limit);
        return todaysFriends.stream()
                .map(UserBoardResponseDto::new)
                .collect(Collectors.toList());
    }
}
