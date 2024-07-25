package com.pallow.pallow.domain.chat.controller;

import com.pallow.pallow.domain.chat.dto.ChatMessageDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomResponseDto;
import com.pallow.pallow.domain.chat.entity.ChatMessage;
import com.pallow.pallow.domain.chat.model.WebSocketChatMessage;
import com.pallow.pallow.domain.chat.model.MessageType;
import com.pallow.pallow.domain.chat.service.ChatService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.createRoom")
    public ChatRoomDto createRoom(@Payload String roomName, @Payload String username) {
        ChatRoomDto chatRoomDto = chatService.createChatRoom(roomName, username);
        messagingTemplate.convertAndSend("/topic/room/" + chatRoomDto.getId(), chatRoomDto);
        return chatRoomDto;
    }

    @MessageMapping("/chat.enterRoom")
    public ChatRoomResponseDto enterRoom(@Payload Long roomId, @Payload String username) {
        ChatRoomResponseDto responseDto = chatService.enterChatRoom(roomId, username);
        messagingTemplate.convertAndSend("/topic/room/" + roomId, responseDto);
        return responseDto;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public WebSocketChatMessage addUser(@Payload WebSocketChatMessage webSocketChatMessage,
            SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getSender());
        webSocketChatMessage.setType(MessageType.JOIN);
        messagingTemplate.convertAndSend("/topic/room/" + webSocketChatMessage.getRoomId(), webSocketChatMessage);
        return webSocketChatMessage;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setType(MessageType.CHAT);
        return chatMessage;
    }

    @MessageMapping("/chat.getRooms")
    public List<ChatRoomDto> getChatRooms(@Payload String username) {
        try {
            logger.info("Fetching chat rooms for user: {}", username);
            return chatService.getChatRoomsForUser(username);
        } catch (Exception e) {
            logger.error("Error fetching chat rooms for user: {}", username, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching chat rooms", e);
        }
    }
}
