package com.pallow.pallow.domain.chat.controller;

import com.pallow.pallow.domain.chat.dto.ChatMessageDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomResponseDto;
import com.pallow.pallow.domain.chat.model.WebSocketChatMessage;
import com.pallow.pallow.domain.chat.model.MessageType;
import com.pallow.pallow.domain.chat.service.ChatService;
import com.pallow.pallow.global.security.UserDetailsImpl;
import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserDetailsService userDetailsService;

    @Autowired
    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate, UserDetailsService userDetailsService) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
        this.userDetailsService = userDetailsService;
    }
    @MessageMapping("/chat.createRoom")
    public ChatRoomDto createRoom(@Payload String roomName, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ChatRoomDto chatRoomDto = chatService.createChatRoom(roomName, userDetails);
        messagingTemplate.convertAndSend("/topic/room/" + chatRoomDto.getId(), chatRoomDto);
        return chatRoomDto;
    }

    @MessageMapping("/chat.enterRoom")
    public ChatRoomResponseDto enterRoom(@Payload Long roomId) {
        ChatRoomResponseDto responseDto = chatService.enterChatRoom(roomId);
        messagingTemplate.convertAndSend("/topic/room/" + roomId, responseDto);
        return responseDto;
    }

    @MessageMapping("/chat.addUser")
    public WebSocketChatMessage addUser(@Payload WebSocketChatMessage webSocketChatMessage, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getSender());
        webSocketChatMessage.setType(MessageType.JOIN);
        messagingTemplate.convertAndSend("/topic/room/" + webSocketChatMessage.getRoomId(), webSocketChatMessage);
        return webSocketChatMessage;
    }

    @MessageMapping("/chat.sendMessage")
    public ChatMessageDto sendMessage(@Payload ChatMessageDto chatMessageDto, Principal principal) {
        if (principal == null) {
            throw new IllegalArgumentException("User not authenticated");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(principal.getName());
        ChatMessageDto savedMessage = chatService.sendAndSaveMessage(chatMessageDto, userDetails);
        messagingTemplate.convertAndSend("/topic/room/" + chatMessageDto.getChatRoomId(), savedMessage);
        return savedMessage;
    }
}
