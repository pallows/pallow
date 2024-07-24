package com.pallow.pallow.domain.chat.service;

import com.pallow.pallow.domain.chat.dto.ChatMessageDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomResponseDto;
import com.pallow.pallow.domain.chat.entity.ChatMessage;
import com.pallow.pallow.domain.chat.entity.ChatRoom;
import com.pallow.pallow.domain.chat.repository.ChatMessageRepository;
import com.pallow.pallow.domain.chat.repository.ChatRoomRepository;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.security.UserDetailsImpl;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatRoomDto createChatRoom(String name, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .sender(user)
                .build();
        chatRoom = chatRoomRepository.save(chatRoom);
        return convertToChatRoomDto(chatRoom);
    }

    public ChatRoomResponseDto enterChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
        List<ChatMessage> messages = chatMessageRepository.findByChatRoomIdOrderByCreatedAtAsc(
                chatRoomId);

        return new ChatRoomResponseDto(
                convertToChatRoomDto(chatRoom),
                messages.stream().map(this::convertToChatMessageDto).collect(Collectors.toList())
        );
    }

    public ChatMessageDto sendAndSaveMessage(ChatMessageDto messageDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
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