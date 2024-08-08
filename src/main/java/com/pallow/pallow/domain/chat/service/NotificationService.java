package com.pallow.pallow.domain.chat.service;

import com.pallow.pallow.domain.chat.entity.ChatRoom;
import com.pallow.pallow.domain.chat.model.MessageType;
import com.pallow.pallow.domain.chat.model.WebSocketChatMessage;
import com.pallow.pallow.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendChatInvitation(User invitedUser, User creator, ChatRoom chatRoom) {
        String message = String.format("%s님이 '%s' 채팅방에 초대했습니다.",
                creator.getNickname(), chatRoom.getName());

        WebSocketChatMessage notification = new WebSocketChatMessage();
        notification.setType(MessageType.INVITATION);
        notification.setSender(creator.getNickname());
        notification.setContent(message);
        notification.setRoomId(chatRoom.getId().toString());

        messagingTemplate.convertAndSendToUser(
                invitedUser.getUsername(),
                "/queue/notifications",
                notification
        );
    }
}

