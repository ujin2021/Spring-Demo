package com.example.jwtdemo.controller;

import com.example.jwtdemo.dto.request.LoginDto;
import com.example.jwtdemo.dto.request.TokenRequestDto;
import com.example.jwtdemo.dto.response.TokenDto;
import com.example.jwtdemo.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) { // loginDto로 post 요청으로 들어온 속성을 받고
        log.info("userId = {}, password = {}", loginDto.getUserId(), loginDto.getPassword());
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        authService.logout();
        return new ResponseEntity(HttpStatus.OK); // status code만 return
    }
}
