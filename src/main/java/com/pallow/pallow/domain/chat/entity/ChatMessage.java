package com.pallow.pallow.domain.chat.entity;

import com.pallow.pallow.domain.chat.model.MessageType;
import com.pallow.pallow.global.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String sender;

    @Setter
    @Column(nullable = false)
    private String content;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type;

    @Column
    public LocalDateTime getTimestamp() {
        return getCreatedAt();
    }

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;
}