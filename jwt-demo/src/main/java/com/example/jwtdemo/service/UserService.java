package com.example.jwtdemo.service;

import com.example.jwtdemo.exception.DuplicateMemberException;
import com.example.jwtdemo.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import com.example.jwtdemo.dto.UserDto;
import com.example.jwtdemo.entity.Authority;
import com.example.jwtdemo.entity.User;
import com.example.jwtdemo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User signup(UserDto userDto) {
        // DB에 저장되어 있는지 확인
        if (userRepository.findOneWithAuthoritiesByUserId(userDto.getUserId()).orElse(null) != null) {
            // throw new RuntimeException("이미 가입되어 있는 유저 입니다."); // 기존 error 처리
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다."); // 새로운 exception class 생성
        }

        // DB에 없다면 권한정보를 만든다 - 회원가입을 통해 만들어진 user는 User 권한을 가진다
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER") // ROLE_USER 이라는 권한을 가진다
                .build();

        // builder pattern의 장점
        // User 정보를 만든다
        User user = User.builder()
                .userId(userDto.getUserId())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authorities(Collections.singleton(authority)) // set : Collections.singleton(T o) 단일원소일때 사용
                .build();

        // User 정보와 권한정보를 저장한다
        return userRepository.save(user);
    }

    // userId를 parameter로 받고 user객체와 권한정보를 갖고 올 수 있는 method
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String userId) {
        return userRepository.findOneWithAuthoritiesByUserId(userId);
    }

    // 현재 security context에 저장되어 있는 userId에 해당하는 user정보와 권한정보만 받아갈 수 있다
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
//        현재 사용자의 userId를 가져오는 util성 method
//        String userId = SecurityUtil.getCurrentUserId().get();
//        log.info("userId1 = {}", userId); // admin
//
//        Optional<User> u = SecurityUtil.getCurrentUserId().flatMap(userRepository::findOneWithAuthoritiesByUserId);
//        log.info("user = {}", u.get()); // User object
//        log.info("userId2 = {}", u.get().getUserId()); // admin
        return SecurityUtil.getCurrentUserId().flatMap(userRepository::findOneWithAuthoritiesByUserId);
    }
}