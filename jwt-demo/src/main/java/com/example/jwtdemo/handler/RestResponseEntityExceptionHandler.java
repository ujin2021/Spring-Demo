package com.example.jwtdemo.handler;

import com.example.jwtdemo.dto.ErrorDto;
import com.example.jwtdemo.exception.DuplicateMemberException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice // application의 예외처리를 맡음
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(CONFLICT) // 예외의 응답상태
    @ExceptionHandler(value = { DuplicateMemberException.class })
    @ResponseBody
    protected ErrorDto badRequest(RuntimeException ex, WebRequest request) {
        return new ErrorDto(CONFLICT.value(), ex.getMessage());
    }
}
/*
참고 : https://jeong-pro.tistory.com/195
[@ExceptionHandler]
@Controller, @RestController가 적용된 Bean내에서 발생하는 예외를 잡아 하나의 method에서 처리해주는 기능
@ExceptionHandler annotation을 쓰고, 인자로 catch 하고 싶은 예외 class를 등록해주면 된다
현재 signup시 중복되는 회원일때 DuplicateMemberException이 발생한다
여러개의 예외 클래스를 등록해줄 수 있다

@ExceptionHandler를 등록한 Controller에만 적용된다. 다른 Controller에서 예외 발생시 예외처리가 불가능하다

[@ControllerAdvice]
@ExceptionHandler는 하나의 클래스에 대한것
@ControllerAdvice는 모든 @Controller. 전역에서 발생할 수 있는 예외를 잡아 처리해준다

[예외처리]
try/catch - method 단위
@ExceptionHandler - Controller 단위
@ControllerAdvice - 전역

*/