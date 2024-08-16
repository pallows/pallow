package com.pallow.pallow.domain.chat.entity;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.entity.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAndChatRoom extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id")
    private User user2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;


    public UserAndChatRoom(User user1, User user2, ChatRoom chatRoom) {
        if (user1.equals(user2)) {
            throw new IllegalArgumentException("User1 and User2 must be different");
        }
        this.user1 = user1;
        this.user2 = user2;
        this.chatRoom = chatRoom;
        this.isActive = true;
        this.isRead = false;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private boolean isRead;

    public void markAsRead() {
        this.isRead = true;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        return users;
    }
}