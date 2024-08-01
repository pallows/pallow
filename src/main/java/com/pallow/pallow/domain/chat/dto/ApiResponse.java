package com.pallow.pallow.domain.chat.dto; // 적절한 패키지로 변경하세요

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pallow.pallow.global.enums.ErrorType;
import com.pallow.pallow.global.enums.Message;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Object data;

    // Message와 ErrorType enum을 사용하는 생성자
    public ApiResponse(Message message, Object data) {
        this.message = message.getMessage();
        this.data = data;
    }

    public ApiResponse(ErrorType errorType, Object data) {
        this.message = errorType.getMessage();
        this.data = data;
    }
}