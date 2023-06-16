package com.oneline.shimpyo.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.security.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.*;
import static com.oneline.shimpyo.security.jwt.JwtConstants.JWT_SECRET;
import static com.oneline.shimpyo.security.jwt.JwtConstants.RT_EXP_TIME;

@Slf4j
@Component
public class JwtTokenUtil {
    public static String generateToken(PrincipalDetails member, boolean isMember, long EXP_TIME) {
            return JWT.create()
                    .withSubject(member.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXP_TIME))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withClaim("isMember", isMember)
                    .withClaim("username", member.getMember().getEmail())
                    .withClaim("id", member.getMember().getId())
                    .sign(HMAC256(JWT_SECRET));
    }

    public static String generateNonMemberToken(String username, boolean isMember, long EXP_TIME) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("isMember", isMember)
                .withClaim("username", username)
                .sign(HMAC256(JWT_SECRET));
    }

    // 재발급 토큰
    public static String reissuanceAccessToken(Member member, boolean isMember, long EXP_TIME, long now) {
        return JWT.create()
                .withSubject(member.getEmail())
                .withExpiresAt(new Date(now + EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("isMember", isMember)
                .withClaim("username", member.getEmail())
                .withClaim("id", member.getId())
                .sign(HMAC256(JWT_SECRET));
    }

    public static String ressuanceRefreshToken(Member member, boolean isMember, long EXP_TIME, long now) {
        return JWT.create()
                .withSubject(member.getEmail())
                .withExpiresAt(new Date(now + RT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("username", member.getEmail())
                .withClaim("id", member.getId())
                .sign(HMAC256(JWT_SECRET));
    }

    // 토큰 확인
    public static boolean validateToken(String token) {
        try {
            JWT.require(HMAC256(JWT_SECRET)).build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 회원인지 아닌지
    public static boolean isMember(String token) {
        return JWT.require(HMAC256(JWT_SECRET)).build()
                .verify(token)
                .getClaim("isMember")
                .asBoolean();
    }

    public static String getUsernameFromToken(String token) {
        return JWT.require(HMAC256(JWT_SECRET)).build()
                .verify(token)
                .getClaim("username")
                .asString();
    }

    public static Long getIdFromToken(String token) {
        return JWT.require(HMAC256(JWT_SECRET)).build()
                .verify(token)
                .getClaim("id")
                .asLong();
    }

    public Authentication generateAnonymousToken() {
        return null;
    }
}
