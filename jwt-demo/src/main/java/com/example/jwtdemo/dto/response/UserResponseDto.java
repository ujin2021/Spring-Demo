package com.example.jwtdemo.dto.response;

import com.example.jwtdemo.entity.Authority;
import com.example.jwtdemo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto { // user 정보 반환시 사용

    private String userId;
    private Set<Authority> authorities;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUserId(), user.getAuthorities());
    }
}
