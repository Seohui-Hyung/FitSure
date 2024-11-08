package com.pretzero.fitsure.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret"; // 256비트 시크릿 키

    // JWT 토큰 발급
    public static String generateToken(String loginId, long expireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰에서 loginId 클레임 추출
    public static String getLoginId(String token) {
        Claims claims = extractClaims(token);
        return claims != null ? claims.get("loginId", String.class) : null;
    }

    // JWT 토큰 만료 여부 확인
    public static boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate != null && expiredDate.before(new Date());
    }

    // JWT 토큰 파싱 (시크릿 키로 검증)
    private static Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(getSigningKey())
                       .build()
                       .parseClaimsJws(token)
                       .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired: " + e.getMessage());
            return null;
        } catch (JwtException e) {
            System.out.println("Token parsing error: " + e.getMessage());
            return null;
        }
    }

    // 서명 키 생성
    private static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
