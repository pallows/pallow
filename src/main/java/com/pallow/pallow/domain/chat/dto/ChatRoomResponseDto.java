package com.pallow.pallow.domain.chat.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

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