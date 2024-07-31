package com.pallow.pallow.domain.chat.service;

import com.pallow.pallow.domain.chat.controller.ChatController;
import com.pallow.pallow.domain.chat.dto.ChatMessageDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomResponseDto;
import com.pallow.pallow.domain.chat.entity.ChatMessage;
import com.pallow.pallow.domain.chat.entity.ChatRoom;
import com.pallow.pallow.domain.chat.entity.UserAndChatRoom;
import com.pallow.pallow.domain.chat.repository.ChatMessageRepository;
import com.pallow.pallow.domain.chat.repository.ChatRoomRepository;
import com.pallow.pallow.domain.chat.repository.UserAndChatRoomRepository;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserAndChatRoomRepository userAndChatRoomRepository;
    private final UserRepository userRepository;  // UserRepository를 추가합니다.
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public ChatRoomDto createChatRoom(String name, String username) {
        User user = findUserByUsername(username);
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .sender(user)
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

    public ChatRoomResponseDto enterChatRoom(Long chatRoomId, String username) {
        User user = findUserByUsername(username);
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

    public ChatMessageDto sendAndSaveMessage(ChatMessageDto messageDto, String username) {
        User user = findUserByUsername(username);
        ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getChatRoomId())
                .orElseThrow(() -> new RuntimeException("Chatroom not found"));

        ChatMessage message = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(user.getUsername())
                .content(messageDto.getContent())
                .type(messageDto.getType())
                .build();

        message = chatMessageRepository.save(message);
        return convertToChatMessageDto(message);
    }

    public List<ChatRoomDto> getChatRoomsForUser(String username) {
        User user = findUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        List<UserAndChatRoom> userChatRooms = userAndChatRoomRepository.findByUserAndIsActiveTrue(user);
        if (userChatRooms.isEmpty()) {
            logger.info("No chat rooms found for user: {}", user.getUsername());
        }
        return userChatRooms.stream()
                .map(ucr -> convertToChatRoomDto(ucr.getChatRoom()))
                .collect(Collectors.toList());
    }

    private ChatRoomDto convertToChatRoomDto(ChatRoom chatRoom) {
        ChatRoomDto dto = new ChatRoomDto();
        dto.setId(chatRoom.getId());
        dto.setName(chatRoom.getName());
        return dto;
    }

    public ChatMessageDto convertToChatMessageDto(ChatMessage message) {
        return ChatMessageDto.builder()
                .id(message.getId())
                .chatRoomId(message.getChatRoom().getId())
                .sender(message.getSender())
                .content(message.getContent())
                .type(message.getType())
                .createdAt(message.getCreatedAt())
                .modifiedAt(message.getModifiedAt())
                .build();
    }
}
