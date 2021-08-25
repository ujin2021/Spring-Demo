package com.example.jwtdemo.service;

import com.example.jwtdemo.dto.request.LoginDto;
import com.example.jwtdemo.dto.request.TokenRequestDto;
import com.example.jwtdemo.dto.response.TokenDto;
import com.example.jwtdemo.entity.RefreshToken;
import com.example.jwtdemo.exception.AuthException;
import com.example.jwtdemo.jwt.TokenProvider;
import com.example.jwtdemo.repository.RefreshTokenRepository;
import com.example.jwtdemo.repository.UserRepository;
import com.example.jwtdemo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDto login(LoginDto loginDto) {

        // 1. Login id, pw를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());

        // 2. 실제로 검증이 이루어지는 부분 (사용자 비밀번호 체크)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성(accessToken, refreshToken)
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        // 5. token 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {

        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new AuthException("RefreshToken이 유효하지 않습니다");
        }

        // 2. AccessToken에서 userId 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 userId를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new AuthException("로그아웃 된 사용자입니다"));

        // 4. RefreshToken이 일치하는지 검사 (사용자 req body의 refreshToken == repository의 refreshToken)
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new AuthException("토큰의 유저 정보가 일치하지 않습니다");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 7. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public void logout() {

        String userId = SecurityUtil.getCurrentUserId().get();
        refreshTokenRepository.deleteByKey(userId);
    }
}
