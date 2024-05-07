package com.wolopolo.oauth2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommonResponse {
    public static final int OK = 200;
    public static final int BUSINESS_ERROR = 400;
    public static final String DEFAULT_BUSINESS_ERROR_CODE = "error.business";
    public static final int UNAUTHORIZED = 403;
    public static final String DEFAULT_UNAUTHORIZED_CODE = "error.unauthorized";

    private int status;
    private String code;
    private String message;
    private Object body;

    public static CommonResponse returnOk(Object body) {
        return CommonResponse.builder()
                .status(OK)
                .body(body)
                .build();
    }

    public static CommonResponse returnError(int status, String code, String message) {
        return CommonResponse.builder()
                .status(status)
                .code(code)
                .message(message)
                .build();
    }

    public static CommonResponse returnBusinessError(String code, String message) {
        return returnError(BUSINESS_ERROR, code, message);
    }

    public static CommonResponse returnUnauthorized(String code, String message) {
        return returnError(UNAUTHORIZED, code, message);
    }
}
