package com.example.jwtdemo.exception;

public class MemberNotExistException extends RuntimeException{
    public MemberNotExistException() {
        super();
    }
    public MemberNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    public MemberNotExistException(String message) {
        super(message);
    }
    public MemberNotExistException(Throwable cause) {
        super(cause);
    }
}
