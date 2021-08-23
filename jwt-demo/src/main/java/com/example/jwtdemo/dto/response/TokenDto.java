package com.example.jwtdemo.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto { // token response시 사용

    private String token;
}
