package com.pallow.pallow.domain.chat.entity;

import com.pallow.pallow.domain.meets.entity.Meets;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.entity.TimeStamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Column
    private LocalDateTime deletedAt;


    @Builder.Default
    @Column(nullable = false)
    private boolean isDeleted = false;


    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAndChatRoom> userAndChatRooms = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meets_id")
    private Meets meets;

    // users 대신 userAndChatRooms를 사용하는 메서드들 추가
    public List<User> getUsers() {
        return userAndChatRooms.stream()
                .flatMap(ucr -> ucr.getUsers().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public void addUserAndChatRoom(UserAndChatRoom userAndChatRoom) {
        userAndChatRooms.add(userAndChatRoom);
        userAndChatRoom.setChatRoom(this);
    }

    public void removeUserAndChatRoom(UserAndChatRoom userAndChatRoom) {
        userAndChatRooms.remove(userAndChatRoom);
        userAndChatRoom.setChatRoom(null);
    }

    public void setMeets(Meets meets) {
        this.meets = meets;
    }

    public void addUsers(User user1, User user2) {
        UserAndChatRoom userAndChatRoom = new UserAndChatRoom(user1, user2, this);
        userAndChatRooms.add(userAndChatRoom);
        user1.addUserAndChatRoom(userAndChatRoom);
        user2.addUserAndChatRoom(userAndChatRoom);
    }

    public void removeUser(User user) {
        userAndChatRooms.removeIf(ucr -> ucr.getUser1().equals(user) || ucr.getUser2().equals(user));
    }

    public int getUserCount() {
        return userAndChatRooms.size() * 2;
    }

    // 채팅방에 들어있는 유저가 중복 유저인지 아닌지 확인
    public boolean hasUser(User user) {
        return userAndChatRooms.stream()
                .anyMatch(ucr -> ucr.getUser1().equals(user) || ucr.getUser2().equals(user));
    }
}