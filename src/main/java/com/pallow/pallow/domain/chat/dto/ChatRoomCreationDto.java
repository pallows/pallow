package com.pallow.pallow.domain.chat.dto;

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
public class ChatRoomCreationDto {
    private String name;
    private String invitedUserNickname;

    // getters and setters
}
