package com.pallow.pallow.domain.chat.dto;

import com.pallow.pallow.domain.chat.model.MessageType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChatMessageDto {

        private Long id;
        private Long chatRoomId;
        private String sender;
        private String content;
        private MessageType type;
        private LocalDateTime createdAt;
        private String formattedTime;
        private int chatReactionCount;

        /**
         * 현재 사용자의 반응 여부
         */
        private boolean hasChatReacted;

        @Builder.Default
        ChatMessageDto message = ChatMessageDto.builder()
                .id(1L)
                .chatRoomId(2L)
                .sender("User")
                .content("Hello")
                .type(MessageType.CHAT)
                .createdAt(LocalDateTime.now())
                .formattedTime("10:30 AM")
                .chatReactionCount(5)
                .hasChatReacted(true)
                .build();
}
