package com.pallow.pallow.domain.user.entity;

// ... (기존 import문)

import com.pallow.pallow.global.entity.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
public class User extends TimeStamp {
    // ... (기존 코드 유지)
}

// ChatMessage.java (새로 추가)
package com.pallow.pallow.domain.chat.entity;

import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.entity.TimeStamp;
import jakarta.persistence.*;
        import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ChatMessage")
public class ChatMessage extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Sender와 receiver이라는 두 개의 User참조를 가짐으로서 한 사용자가 여러 메시지를 보내거나 받을 수 있음
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(nullable = false)
    private String message;

}

/**
 * 추후 그룹 채팅 기능을 추가하고 싶다면, ChatMessage에 Meets에 대한 참조를 추가하고 (nullable하게) 설정하면 됩니다.
 * 이렇게 하면 Meets 참조가 null이면 1:1 채팅, 값이 있으면 그룹 채팅으로 구분할 수 있습니다.
 */