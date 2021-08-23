package com.example.jwtdemo.controller;

import com.example.jwtdemo.dto.response.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import com.example.jwtdemo.dto.request.SignupDto;
import com.example.jwtdemo.entity.User;
import com.example.jwtdemo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody SignupDto signupDto
    ) {
        return ResponseEntity.ok(userService.signup(signupDto));
    }

//    // ROLE에 따른 접근 권한
//    @GetMapping("/user")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//    public ResponseEntity<User> getMyUserInfo() {
//        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
//    }
//
//    @GetMapping("/user/{userId}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<User> getUserInfo(@PathVariable String userId) {
//        return ResponseEntity.ok(userService.getUserWithAuthorities(userId).get());
//    }

    // userId + authorities 반환
    // admin user는 userId로 모든 user의 정보를 조회 가능
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getMemberInfo(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getMemberInfo(userId));
    }

    // 내 정보 반환 (userId + authorities)
    // 일반 유저는 자신의 정보만 조회 가능
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserResponseDto> getMyInfo() {
        return ResponseEntity.ok(userService.getMyInfo());
    }

}
