package com.pallow.pallow.global.dtos;

import static com.nimbusds.oauth2.sdk.http.HTTPResponse.SC_UNAUTHORIZED;

import lombok.Getter;

@Getter
public class UnauthenticatedResponse {

    private final int statusCode;
    private final String msg;

    public UnauthenticatedResponse(String msg) {
        this.statusCode = SC_UNAUTHORIZED;
        this.msg = msg;
    }

}