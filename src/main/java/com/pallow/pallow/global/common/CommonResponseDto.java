package com.pallow.pallow.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponseDto<T> {

    private int statusCode;
    private String message;
    private T data;

}
