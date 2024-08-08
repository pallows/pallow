package com.pallow.pallow.domain.chat.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class ChatRoomResponseDto {
    private ChatRoomDto chatRoom;
    private List<ChatMessageDto> messages;

    // 기존 생성자 유지
    public ChatRoomResponseDto(ChatRoomDto chatRoom, List<ChatMessageDto> messages) {
        this.chatRoom = chatRoom;
        this.messages = messages;
    }
}