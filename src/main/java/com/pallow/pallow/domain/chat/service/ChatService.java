package com.pallow.pallow.domain.chat.service;

import com.pallow.pallow.domain.chat.controller.ChatWebsocketController;
import com.pallow.pallow.domain.chat.dto.ChatMessageDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomResponseDto;
import com.pallow.pallow.domain.chat.entity.ChatMessage;
import com.pallow.pallow.domain.chat.entity.ChatReaction;
import com.pallow.pallow.domain.chat.entity.ChatRoom;
import com.pallow.pallow.domain.chat.entity.UserAndChatRoom;
import com.pallow.pallow.domain.chat.event.ChatInvitationEvent;
import com.pallow.pallow.domain.chat.model.MessageType;
import com.pallow.pallow.domain.chat.repository.ChatMessageRepository;
import com.pallow.pallow.domain.chat.repository.ChatRoomRepository;
import com.pallow.pallow.domain.chat.repository.UserAndChatRoomRepository;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.exception.CustomException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import jdk.jshell.spi.ExecutionControl.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChatService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserAndChatRoomRepository userAndChatRoomRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ChatWebsocketController.class);

    private User findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Nickname not found"));
    }

    /**
     * 채팅방 생성
     * @param chatRoomDto 채팅방 정보
     * @param nickname 유저의 닉네임
     * @return
     */
    public ChatRoomDto createChatRoom(ChatRoomDto chatRoomDto, String nickname) {
        User user = findUserByNickname(nickname);
        ChatRoom chatRoom = ChatRoom.builder()
                .name(chatRoomDto.getName())
                .sender(user)
                .inviteCode(generateInviteCode()) // 이 부분 추가
                .build();
        chatRoom = chatRoomRepository.save(chatRoom);

        UserAndChatRoom userAndChatRoom = UserAndChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .isActive(true)
                .build();
        userAndChatRoomRepository.save(userAndChatRoom);

        return convertToChatRoomDto(chatRoom);
    }


    /**
     * 채팅방 입장
     * @param chatRoomId 채팅방 ID
     * @param nickname 유저의 닉네임
     * @return
     */
    public ChatRoomResponseDto enterChatRoom(Long chatRoomId, String nickname) {
        User user = findUserByNickname(nickname);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        UserAndChatRoom userAndChatRoom = userAndChatRoomRepository.findByUserAndChatRoom(user, chatRoom)
                .orElseGet(() -> {
                    UserAndChatRoom newUserAndChatRoom = UserAndChatRoom.builder()
                            .user(user)
                            .chatRoom(chatRoom)
                            .isActive(true)
                            .build();
                    return userAndChatRoomRepository.save(newUserAndChatRoom);
                });

        List<ChatMessage> messages = chatMessageRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoomId);

        return new ChatRoomResponseDto(
                convertToChatRoomDto(chatRoom),
                messages.stream().map(this::convertToChatMessageDto).collect(Collectors.toList())
        );
    }

    /**
     * 메시지 보내기 + 저장하기 (db에)
     * @param messageDto
     * @param nickname
     * @return
     */
    public ChatMessageDto sendAndSaveMessage(ChatMessageDto messageDto, String nickname) {
        User user = findUserByNickname(nickname);
        ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getChatRoomId())
                .orElseThrow(() -> new RuntimeException("Chatroom not found"));

        ChatMessage message = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(user.getNickname())
                .content(messageDto.getContent())
                .type(messageDto.getType() != null ? messageDto.getType() : MessageType.CHAT)
                .build();
        log.info("Saving message: {}", message);
        message = chatMessageRepository.save(message);
        return convertToChatMessageDto(message);
    }

    /**
     * 유저가 본인이 참여한 채팅방 리스트 조회
     * @param nickname
     * @return
     */
    public List<ChatRoomDto> getChatRoomsForUser(String nickname) {
        User user = findUserByNickname(nickname);
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        List<UserAndChatRoom> userChatRooms = userAndChatRoomRepository.findByUserIdAndIsActiveTrue(user.getId());
        if (userChatRooms.isEmpty()) {
            logger.info("No chat rooms found for user: {}", user.getNickname());
        }
        return userChatRooms.stream()
                .map(ucr -> convertToChatRoomDto(ucr.getChatRoom()))
                .collect(Collectors.toList());
    }

    /**
     * chatroom엔티티 dto로 바꾸는 로직
     * @param chatRoom
     * @return
     */
    private ChatRoomDto convertToChatRoomDto(ChatRoom chatRoom) {
        return ChatRoomDto.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .creatorNickname(chatRoom.getSender().getNickname())
                .build();
    }


    /**
     * chatmessage엔티티 dto로 바꾸는 로직
     * 메시지 말풍선에 시간 설정로직 추가(chatmessageDto 참고)
     * ex) 08:30 PM 혹은 12:00 AM 이런 식으로 시간 설정이 됨
     * 프론트에선 ChatMessageDto의 formattedTime을 활용해 시간 표시 할 수 있음.
     */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");
    public ChatMessageDto convertToChatMessageDto(ChatMessage message) {
        return convertToChatMessageDto(message, null);
    }

    @Transactional
    public ChatMessageDto toggleChatReaction(Long messageId, Long userId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        /**
         * r = 각각의 reaction 객체
         * 현재 메시지에 대해 특정 사용자(userId)가 남긴 반응이 있는지 찾고, 있다면 그 반응을 Optional 형태로 변환
         * 사용자가 이미 반응을 남겼는지 확인하고 그에 따라 반응을 추가하거나 제거하는 작업 수행 가능 (toggle 형태이기 때문)
         */
        Optional<ChatReaction> existingChatReaction = message.getChatReactions().stream()
                .filter(r -> r.getUser().getId().equals(userId))
                .findFirst();

        /**
         * if문 : 만약 존재하는 공감이 있다면 -> 지울 수 있게 하고(userId를 비교해서 같은 userId + 토글 형식이니 첫 번째 누를 때는 좋아요겠지만 같은 유저가 같은 메시지를 두 번째 누르면 좋아요 취소!
         * 좋아요 카운트 감소
         * else : 만약 존재하는 공감이 없다면 -> 공감을 추가하게 함(user정보를 담아서)
         */
        if (existingChatReaction.isPresent()) {
            message.removeChatReaction(existingChatReaction.get());
            message.decrementChatReactionCount();
        } else {
            message.addChatReaction(user);
            message.incrementChatReactionCount();
        }

        chatMessageRepository.save(message);
        return convertToChatMessageDto(message, userId);
    }

    private ChatMessageDto convertToChatMessageDto(ChatMessage message, Long userId) {
        ChatMessageDto.ChatMessageDtoBuilder builder = ChatMessageDto.builder()
                .id(message.getId())
                .chatRoomId(message.getChatRoom().getId())
                .sender(message.getSender())
                .content(message.getContent())
                .type(message.getType())
                .createdAt(message.getCreatedAt())
                .formattedTime(message.getCreatedAt().format(TIME_FORMATTER))
                .chatReactionCount(message.getChatReactionCount());

        if (userId != null) {
            builder.hasChatReacted(message.getChatReactions().stream()
                    .anyMatch(r -> r.getUser().getId().equals(userId)));
        }
        return builder.build();
    }

    public Map<Long, Integer> getUnreadCountsForUser(Long userId) {
        Map<Long, Integer> unreadCounts = new HashMap<>();
        List<UserAndChatRoom> userChatRooms = userAndChatRoomRepository.findByUserIdAndIsActiveTrue(userId);

        for (UserAndChatRoom userChatRoom : userChatRooms) {
            Long roomId = userChatRoom.getChatRoom().getId();
            LocalDateTime lastReadTime = userChatRoom.getUpdatedAt();
            int unreadCount = chatMessageRepository.countByCreatedAtAfterAndChatRoomId(lastReadTime, roomId);
            unreadCounts.put(roomId, unreadCount);
        }

        return unreadCounts;
    }

    @Transactional
    public ChatRoomDto findOrCreateChatRoom(Long userId, String username) {
        User currentUser = findUserByNickname(username);
        User otherUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> users = Arrays.asList(currentUser, otherUser);
        Optional<ChatRoom> existingRoom = chatRoomRepository.findByUsersIn(users);

        if (existingRoom.isPresent()) {
            return convertToChatRoomDto(existingRoom.get());
        } else {
            ChatRoom newRoom = ChatRoom.builder()
                    .name(currentUser.getNickname() + " & " + otherUser.getNickname())
                    .sender(currentUser)
                    .build();
            newRoom.getUsers().addAll(users);
            newRoom = chatRoomRepository.save(newRoom);

            return convertToChatRoomDto(newRoom);
        }
    }

    public void markRoomAsRead(Long roomId, Long userId) {
        UserAndChatRoom userChatRoom = userAndChatRoomRepository
                .findByUserIdAndChatRoomId(userId, roomId)
                .orElseThrow(() -> new RuntimeException("User is not in this chat room"));

        userChatRoom.setUpdatedAt(LocalDateTime.now());
        userAndChatRoomRepository.save(userChatRoom);
    }


    public ChatRoomDto getChatRoomByInviteCode(String inviteCode) {
        ChatRoom chatRoom = chatRoomRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
        return convertToChatRoomDto(chatRoom);
    }

    public ChatRoomResponseDto addUserToChatRoom(Long chatRoomId, String username, boolean isAnonymous) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        User user;
        if (isAnonymous) {
            user = createAnonymousUser(username);
        } else {
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        UserAndChatRoom userAndChatRoom = userAndChatRoomRepository
                .findByUserAndChatRoom(user, chatRoom)
                .orElseGet(() -> {
                    UserAndChatRoom newUserAndChatRoom = new UserAndChatRoom(user, chatRoom);
                    return userAndChatRoomRepository.save(newUserAndChatRoom);
                });
        List<ChatMessage> messages = chatMessageRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoomId);

        return new ChatRoomResponseDto(
                convertToChatRoomDto(chatRoom),
                messages.stream().map(this::convertToChatMessageDto).collect(Collectors.toList())
        );
    }

    public void leaveChatRoom(Long chatRoomId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        UserAndChatRoom userAndChatRoom = userAndChatRoomRepository
                .findByUserAndChatRoom(user, chatRoom)
                .orElseThrow(() -> new RuntimeException("User is not in this chat room"));

        userAndChatRoomRepository.delete(userAndChatRoom);

        // If the user was the last one in the room, delete the room
        if (chatRoom.getUsers().size() == 1) {
            chatRoomRepository.delete(chatRoom);
        }
    }

    private User createAnonymousUser(String username) {
        User anonymousUser = new User();
        anonymousUser.setUsername(username);
        anonymousUser.setAnonymous(true);
        return userRepository.save(anonymousUser);
    }

    private String generateInviteCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Transactional
    public ChatRoomDto createChatRoomWithUser(String roomName, String creatorNickname, String invitedUserNickname) throws CustomException {
        User creator = findUserByNickname(creatorNickname);
        User invitedUser = findUserByNickname(invitedUserNickname);

        if (invitedUser == null) {
            throw new CustomException(ErrorType.NOT_FOUND_APPLY);
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .name(roomName)
                .sender(creator)
                .build();
        chatRoom = chatRoomRepository.save(chatRoom);

        // ... 기존 코드 ...

        // 이벤트 발생
        applicationEventPublisher.publishEvent(new ChatInvitationEvent(this, creator, invitedUser, chatRoom));

        return convertToChatRoomDto(chatRoom);
    }

    @Transactional
    public void deleteChatRoom(Long roomId, String nickname) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
        User user = findUserByNickname(nickname);

        userAndChatRoomRepository.deleteByUserAndChatRoom(user, chatRoom);

        // 채팅방에 남아있는 사용자 수 확인
        long remainingUsers = userAndChatRoomRepository.countByChatRoom(chatRoom);

        if (remainingUsers == 0) {
            chatRoomRepository.delete(chatRoom);
        }
    }

}

/**
 * 공감 관련
 * 유저 / 메시지 있나 확인 -> 없다면 익셉션(유저가 없습니다 메시지가 없습니다)
 *              -> 유저 / 메시지 있다면 공감 표시가 있나 확인
 *                      -> 공감 표시 있다면 공감 삭제
 *                      -> 공감 표시 없다면 공감 생기게
 *                      이렇게 하면 아무것도 없는 메시지를 눌렀을 때 공감 생성
 *                      공감이 이미 있는 메시지를 클릭했을 때 공감 삭제
 */