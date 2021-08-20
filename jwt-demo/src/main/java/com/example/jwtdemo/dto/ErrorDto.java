package com.example.jwtdemo.dto;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ErrorDto {
    private final int status;
    private final String message;
    private List<FieldError> fieldErrors = new ArrayList<>();

    // signup 중복시 status : 409, message : "이미 가입되어 있는 유저입니다." (from UserService)
    public ErrorDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void addFieldError(String objectName, String path, String message) {
        FieldError error = new FieldError(objectName, path, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
