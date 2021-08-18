package com.example.jwtdemo.service;


import lombok.extern.slf4j.Slf4j;
import com.example.jwtdemo.dto.UserDto;
import com.example.jwtdemo.entity.Authority;
import com.example.jwtdemo.entity.User;
import com.example.jwtdemo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;

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
            throw new RuntimeException("이미 가입되어 있는 유저 입니다.");
        }

        // DB에 없다면 권한정보를 만든다 - 회원가입을 통해 만들어진 user는 User 권한을 가진다
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER") // ROLE_USER 이라는 권한을 가진다
                .build();

        // User 정보를 만든다
        User user = User.builder()
                .userId(userDto.getUserId())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .build();

        // User 정보와 권한정보를 저장한다
        return userRepository.save(user);
    }
}