package com.pallow.pallow.domain.chat.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSocketChatMessage {

    private MessageType type;
    private String content;
    private String sender;
    private String roomId;



}
