package com.pallow.pallow.global.common;

import lombok.Getter;

@Getter
public enum CommonOauth {

    LOCAL(0, "LOCAL"),
    KAKAO(1, "KAKAO");

    private String oauth;
    private int code;

    CommonOauth(int code, String status) {
        this.oauth = status;
        this.code = code;

    }


}
