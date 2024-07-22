package com.pallow.pallow.domain.userboardcomment.service;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.service.UserService;
import com.pallow.pallow.domain.userboardcomment.dto.UserBoardCommentRequestDto;
import com.pallow.pallow.domain.userboardcomment.dto.UserBoardCommentResponseDto;
import com.pallow.pallow.domain.userboardcomment.entity.UserBoardComment;
import com.pallow.pallow.domain.userboardcomment.repository.UserBoardCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBoardCommentService {

    private final UserBoardCommentRepository userBoardCommentRepository;
    private final UserService userService;

    public UserBoardCommentResponseDto createComment(Long userId, Long userBoardId, User user,
            UserBoardCommentRequestDto requestDto) {
        User createdBy = userService.findUserById(user.getId());
        UserBoardComment comment = userBoardCommentRepository.save(requestDto.toEntity(createdBy));
        return new UserBoardCommentResponseDto(comment);
    }
}
