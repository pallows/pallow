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
public class ChatRoomDto {

    private Long id;

    /**
     * 채팅방 이름
     */
    private String name;

    /**
     * 채팅방 생성자
     */
    private String creatorNickname;

}
