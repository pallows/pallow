package com.pallow.pallow.domain.userboardcomment.service;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.service.UserService;
import com.pallow.pallow.domain.userboardcomment.dto.UserBoardCommentRequestDto;
import com.pallow.pallow.domain.userboardcomment.dto.UserBoardCommentResponseDto;
import com.pallow.pallow.domain.userboardcomment.entity.UserBoardComment;
import com.pallow.pallow.domain.userboardcomment.repository.UserBoardCommentRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBoardCommentService {

    private final UserBoardCommentRepository userBoardCommentRepository;
    private final UserService userService;

    public UserBoardCommentResponseDto createComment(long userId, User user,
            UserBoardCommentRequestDto requestDto) {
        User createdBy = userService.findUserById(user.getId());
        if (isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }
        UserBoardComment comment = userBoardCommentRepository.save(requestDto.toEntity(createdBy));
        return new UserBoardCommentResponseDto(comment);
    }

    public List<UserBoardCommentResponseDto> getComments(long userBoardId) {
        List<UserBoardComment> comments = userBoardCommentRepository.findAllById(userBoardId);
        return comments.stream().map(UserBoardCommentResponseDto::new).toList();
    }

    public UserBoardCommentResponseDto updateComment(long userId, long commentId,
            User user, UserBoardCommentRequestDto requestDto) {
        UserBoardComment userBoardComment = userBoardCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(
                        ErrorType.NOT_FOUND_USER_BOARD_COMMENT));
        if (isSameIdAndUser(userId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }
        userBoardComment.update(requestDto);
        return new UserBoardCommentResponseDto(userBoardComment);
    }

    public void deleteComment(long commentId, User user) {
        if (isSameIdAndUser(commentId, user)) {
            throw new CustomException(ErrorType.USER_MISMATCH_ID);
        }
        userBoardCommentRepository.deleteById(commentId);
    }

    private boolean isSameIdAndUser(long commentId, User user) {
        return user.getId().equals(commentId);
    }
}
