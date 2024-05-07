package com.wolopolo.oauth2.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessException extends RuntimeException {
    private String code;

    public BusinessException (String code, String message) {
        super(message);
        this.code = code;
    }
}
