package com.pallow.pallow.domain.chat.entity;

import com.pallow.pallow.domain.chat.model.MessageType;
import com.pallow.pallow.global.entity.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
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
