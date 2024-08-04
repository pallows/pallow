package com.pallow.pallow.domain.chat.entity;

import com.pallow.pallow.domain.chat.model.MessageType;
import com.pallow.pallow.domain.user.entity.User;
import com.pallow.pallow.global.entity.TimeStamp;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
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

    /**
     * 추후 프론트에서 채팅에 참여했을 때 "{}, user의 nickname" join, 채팅방에서 나갔을 때 "{}, user의 nickname" leave 로 뜨게 해야설정
     */
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type = MessageType.CHAT;

    /**
     * 기본 메시지의 공감 수를 0개로 초기화 해놓음
     */
    @Column
    private int chatReactionCount = 0;


    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "chatMessage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatReaction> chatReactions = new HashSet<>();


    /**
     * 메시지에 공감 추가 (두 번 클릭하면 추가 되게 설정할 예정)
     */
    public void addChatReaction(User user) {
        ChatReaction chatReaction = new ChatReaction();
        chatReaction.setChatMessage(this);
        chatReaction.setUser(user);
        this.chatReactions.add(chatReaction);
    }

    /**
     * 메시지에 공감 삭제(user1이 남긴 좋아요는 user1만 삭제할 수 있음)
     */
    public void removeChatReaction(ChatReaction chatReaction) {
        this.chatReactions.remove(chatReaction);
    }

    /**
     * 공감 수 증가 로직
     */
    public void incrementChatReactionCount() {
        this.chatReactionCount++;
    }

    /**
     * 공감 수 감소 로직 (공감 수가 0초과일 경우)
     */
    public void decrementChatReactionCount() {
        if(this.chatReactionCount > 0) {
            this.chatReactionCount--;
        }
    }


    /**
     * 메시지 하나에 달려있는 공감 수 조회
     * @return
     */
    public int getChatReactionCount() {
        return this.chatReactionCount;
    }
}