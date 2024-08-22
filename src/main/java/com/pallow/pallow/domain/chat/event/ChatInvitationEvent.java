package com.pallow.pallow.domain.chat.event;

import com.pallow.pallow.domain.chat.entity.ChatRoom;
import com.pallow.pallow.domain.user.entity.User;
import org.springframework.context.ApplicationEvent;

public class ChatInvitationEvent extends ApplicationEvent {
    private final User creator;
    private final User invitedUser;
    private final ChatRoom chatRoom;

    public ChatInvitationEvent(Object source, User creator, User invitedUser, ChatRoom chatRoom) {
        super(source);
        this.creator = creator;
        this.invitedUser = invitedUser;
        this.chatRoom = chatRoom;
    }

    public User getCreator() {
        return creator;
    }

    public User getInvitedUser() {
        return invitedUser;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }
}