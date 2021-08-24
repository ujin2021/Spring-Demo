package com.example.jwtdemo.jwt;

import com.example.jwtdemo.dto.response.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

// token 생성, token 유효성 검증
// bean이 생성되고, 의존성 주입(TokenProvider) 을 받은 다음에
// 주입받은 secret 값을 BASE64.decode한 다음(afterPropertiesSet) key 변수에 할당
@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";

    // 이 filed 들은 application.yml에 있는 정보로 세팅한다 (생성자로)
    private final String secret;
    private final long ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 1; // 30분
    private final long REFRESH_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    private Key key;

    // application.yml에서 설정한 정보들
    public TokenProvider(
            @Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    // afterPropertiesSet 참고 : BeanFactory에 의해 모든 property 가 설정되고 난 뒤 실행되는 메소드
    // 실행시점의 custom 초기화 로직이 필요하거나 주입받은 property 를 확인하는 용도로 사용 (빈 객체 초기화 시 필요한 코드 구현)
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateTokenDto(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // stream의 item들을 우리가 원하는 자료형으로 변환 가능

        long now = (new Date()).getTime();

        // AccessToken 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRED_TIME);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // payload "sub" : "name"
                .claim(AUTHORITIES_KEY, authorities) // payload "auth" : "ROLE_NAME"
                .signWith(key, SignatureAlgorithm.HS512) // header "alg" : "HS512"
                .setExpiration(accessTokenExpiresIn)
                .compact();

        // RefreshToken 생성 (내용 없음)
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRED_TIME);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    // token으로 부터 인증정보 반환
    public Authentication getAuthentication(String token) {
        // token을 이용해서 claim을 만들어 준다 (claim : 사용자에 대한 프로퍼티. 속성)
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // claim에서 권한정보를 빼낸다
        // ? : wildcard. Collection type 모두를 허용
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // 권한정보로 User (entity User 아님) 객체를 만들어준다
        User principal = new User(claims.getSubject(), "", authorities); // UserDetail의 User

        // User와 token, authorities를 사용해 최종적으로 Authentication 객체 리턴
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // token을 파라미터로 받아 유효성 검증을 수행하는 메소드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
