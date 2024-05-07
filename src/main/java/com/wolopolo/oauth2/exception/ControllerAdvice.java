package com.wolopolo.oauth2.exception;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.wolopolo.oauth2.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException businessException) {
        return ResponseEntity
                .status(CommonResponse.BUSINESS_ERROR)
                .body(CommonResponse.returnBusinessError(businessException.getCode(), businessException.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleBusinessException(AccessDeniedException exception) {
        return ResponseEntity
                .status(CommonResponse.UNAUTHORIZED)
                .body(CommonResponse.returnUnauthorized(CommonResponse.DEFAULT_UNAUTHORIZED_CODE, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = String.format("'%s' is invalid", exception.getFieldErrors().get(0).getField());
        return ResponseEntity
                .status(CommonResponse.BUSINESS_ERROR)
                .body(CommonResponse.returnBusinessError(CommonResponse.DEFAULT_BUSINESS_ERROR_CODE, message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleValueInstantiationException(HttpMessageNotReadableException exception) {
        return ResponseEntity
                .status(CommonResponse.BUSINESS_ERROR)
                .body(CommonResponse.returnBusinessError(CommonResponse.DEFAULT_BUSINESS_ERROR_CODE, exception.getMessage()));
    }

    @ExceptionHandler(InvalidBearerTokenException.class)
    public ResponseEntity<Object> handleInvalidBearerTokenException(InvalidBearerTokenException exception) {
        return ResponseEntity
                .status(CommonResponse.UNAUTHORIZED)
                .body(CommonResponse.returnBusinessError(CommonResponse.DEFAULT_UNAUTHORIZED_CODE, exception.getMessage()));
    }
}
