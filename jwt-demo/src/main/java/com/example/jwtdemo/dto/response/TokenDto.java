package com.example.jwtdemo.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto { // token response시 사용

    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
}
