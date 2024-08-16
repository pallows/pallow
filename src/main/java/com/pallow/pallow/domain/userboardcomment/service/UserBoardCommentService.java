package com.pallow.pallow.domain.userboardcomment.service;

import com.pallow.pallow.domain.like.service.LikeService;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.service.UserService;
import com.pallow.pallow.domain.userboard.entity.UserBoard;
import com.pallow.pallow.domain.userboard.repository.UserBoardCustomRepository;
import com.pallow.pallow.domain.userboard.repository.UserBoardRepository;
import com.pallow.pallow.domain.userboardcomment.dto.UserBoardCommentRequestDto;
import com.pallow.pallow.domain.userboardcomment.dto.UserBoardCommentResponseDto;
import com.pallow.pallow.domain.userboardcomment.entity.UserBoardComment;
import com.pallow.pallow.domain.userboardcomment.repository.UserBoardCommentCustomRepository;
import com.pallow.pallow.domain.userboardcomment.repository.UserBoardCommentRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBoardCommentService {

    private final UserService userService;
    private final LikeService likeService;
    private final UserBoardCommentRepository userBoardCommentRepository;
    private final UserBoardRepository userBoardRepository;
    private final UserBoardCustomRepository userBoardCustomRepository;
    private final UserBoardCommentCustomRepository userBoardCommentCustomRepository;


    public UserBoardCommentResponseDto createComment(long boardId, User user,
            UserBoardCommentRequestDto requestDto) {
        User createdBy = userService.findUserById(user.getId());
        UserBoard userBoard = userBoardCustomRepository.findById(boardId);
        UserBoardComment comment = userBoardCommentRepository.save(
                requestDto.toEntity(createdBy, userBoard));
        return new UserBoardCommentResponseDto(comment);
    }

    public List<UserBoardCommentResponseDto> getComments(long userBoardId) {
        List<UserBoardComment> comments = userBoardCommentRepository.findAllByUserBoardId(
                userBoardId);
        return comments.stream().map(UserBoardCommentResponseDto::new).toList();
    }

    @Transactional
    public UserBoardCommentResponseDto updateComment(long commentId,
            User user, UserBoardCommentRequestDto requestDto) {
        UserBoardComment comment = userBoardCommentCustomRepository.findById(commentId);
        if (!isSameIdAndUser(comment.getUser().getId(), user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }
        comment.update(requestDto);
        return new UserBoardCommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(long commentId, User user) {
        UserBoardComment comment = userBoardCommentCustomRepository.findById(commentId);
        if (!isSameIdAndUser(comment.getUser().getId(), user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }
        userBoardCommentRepository.delete(comment);
    }

    /**
     * 좋아요 토글
     *
     * @param userBoardId
     * @param user
     */
    @Transactional
    public void toggleLike(Long userBoardId, User user) {
        likeService.toggleLike(userBoardId, user, userBoardCommentRepository);
    }

    private boolean isSameIdAndUser(long userId, User user) {
        return user.getId().equals(userId);
    }
}
