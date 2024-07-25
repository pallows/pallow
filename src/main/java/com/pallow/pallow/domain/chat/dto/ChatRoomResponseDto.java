package com.pallow.pallow.domain.chat.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomResponseDto {
    private ChatRoomDto chatRoom;
    private List<ChatMessageDto> messages;
    // getters, setters
}
