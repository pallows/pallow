package com.pallow.pallow.domain.chat.controller;

import com.pallow.pallow.domain.chat.model.MessageType;
import com.pallow.pallow.domain.chat.model.WebSocketChatMessage;
import com.pallow.pallow.domain.chat.service.ChatService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@AllArgsConstructor
public class WebSocketEventListener {

    private final ChatService chatService;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User Disconnected : " + username);

            WebSocketChatMessage webSocketChatMessage = new WebSocketChatMessage();
            webSocketChatMessage.setType(MessageType.LEAVE);
            webSocketChatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", webSocketChatMessage);
        }
    }
}