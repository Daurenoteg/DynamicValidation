package com.example.guarantebasic.validator.exceptionhandler;

import com.example.guarantebasic.validator.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusinessException(BusinessException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("errorCode", ex.getCode());
        body.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }
}
