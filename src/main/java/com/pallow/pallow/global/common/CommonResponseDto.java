package com.pallow.pallow.global.common;

import com.pallow.pallow.global.enums.Message;
import lombok.Getter;

@Getter
public class CommonResponseDto {

    private int statusCode;
    private String message;
    private Object data;

    /**
     * 단순히 성공메세지만 반환하고 싶을 때
     * @param message
     */
    public CommonResponseDto(Message message) {
        this.statusCode = message.getStatus().value();
        this.message = message.getMessage();
    }

    /**
     * 반환 값에 만들어진 데이터를 같이 반환하고 싶을 때
     * @param message
     * @param data
     */
    public CommonResponseDto(Message message, Object data) {
        this.statusCode = message.getStatus().value();
        this.message = message.getMessage();
        this.data = data;
    }
}
