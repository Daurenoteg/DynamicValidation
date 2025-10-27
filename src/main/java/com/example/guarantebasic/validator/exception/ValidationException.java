package com.example.guarantebasic.validator.exception;

public class ValidationException extends BusinessException {
    public ValidationException(String code, String message) {
        super(code, message);
    }
}
