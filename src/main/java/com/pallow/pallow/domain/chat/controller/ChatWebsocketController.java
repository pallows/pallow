package com.pallow.pallow.domain.chat.controller;

import ch.qos.logback.classic.ViewStatusMessagesServlet;
import com.pallow.pallow.domain.chat.dto.ChatRoomDto;
import com.pallow.pallow.domain.chat.dto.ChatRoomResponseDto;
import com.pallow.pallow.domain.chat.dto.JoinRoomRequest;
import com.pallow.pallow.domain.chat.dto.LeaveRoomRequest;
import com.pallow.pallow.domain.chat.dto.StatusMessage;
import com.pallow.pallow.domain.chat.entity.ChatMessage;
import com.pallow.pallow.domain.chat.model.MessageType;
import com.pallow.pallow.domain.chat.model.WebSocketChatMessage;
import com.pallow.pallow.domain.chat.service.ChatService;
import com.pallow.pallow.global.security.UserDetailsImpl;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
public class ChatWebsocketController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ChatWebsocketController.class);

    @Autowired
    public ChatWebsocketController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.createRoom")
    public ChatRoomDto createRoom(@Payload ChatRoomDto chatRoomDto, SimpMessageHeaderAccessor headerAccessor) {
        Authentication auth = (Authentication) headerAccessor.getUser();
        String nickname = ((UserDetailsImpl) auth.getPrincipal()).getNickname();
       ChatRoomDto createdChatRoom = chatService.createChatRoom(chatRoomDto, nickname);
       messagingTemplate.convertAndSend("/topic/room/" + createdChatRoom.getId(), createdChatRoom);
       return createdChatRoom;
    }

    @MessageMapping("/chat.enterRoom")
    public ChatRoomResponseDto enterRoom(@Payload Long roomId, SimpMessageHeaderAccessor headerAccessor) {
        Authentication auth = (Authentication) headerAccessor.getUser();
        String nickname = ((UserDetailsImpl) auth.getPrincipal()).getNickname();
        // 사용자의 채팅방 입장 권한 확인 로직 추가
        ChatRoomResponseDto responseDto = chatService.enterChatRoom(roomId, nickname);
        messagingTemplate.convertAndSend("/topic/room/" + roomId, responseDto);
        return responseDto;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public WebSocketChatMessage addUser(@Payload WebSocketChatMessage webSocketChatMessage,
            SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("nickname", webSocketChatMessage.getSender()); // 닉네임을 처음에 굳이 받지 않아도 userImpl에서 회원가입할 때 설정한 Nickname으로 자동참여 할 수 있게 하는게 어떨까..
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
    public List<ChatRoomDto> getChatRooms(@Payload String nickname) {
        try {
            logger.info("Fetching chat rooms for user: {}", nickname);
            return chatService.getChatRoomsForUser(nickname);
        } catch (Exception e) {
            logger.error("Error fetching chat rooms for user: {}", nickname, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching chat rooms", e);
        }
    }

    /**
     * WebSocket 메시지 처리 중 발생하는 예외를 적절히 처리하고 클라이언트에 알림
     * @param exception
     * @return
     */
    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

    @MessageMapping("/chat.joinRoom")
    @SendTo("/topic/public")
    public ChatRoomResponseDto joinRoom(@Payload JoinRoomRequest joinRequest, SimpMessageHeaderAccessor headerAccessor) {
        String username = headerAccessor.getUser().getName();
        ChatRoomResponseDto response = chatService.addUserToChatRoom(
                joinRequest.getRoomId(),
                username,
                joinRequest.isAnonymous()
        );
        headerAccessor.getSessionAttributes().put("room_id", joinRequest.getRoomId());
        return response;
    }

    @MessageMapping("/chat.leaveRoom")
    @SendTo("/topic/public")
    public StatusMessage leaveRoom(@Payload LeaveRoomRequest leaveRequest, SimpMessageHeaderAccessor headerAccessor) {
        String username = headerAccessor.getUser().getName();
        chatService.leaveChatRoom(leaveRequest.getRoomId(), username);
        headerAccessor.getSessionAttributes().remove("room_id");
        return new StatusMessage("User left the room");
    }





}
