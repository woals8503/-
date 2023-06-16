package com.oneline.shimpyo.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.oneline.shimpyo.security.auth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.*;
import static com.oneline.shimpyo.security.jwt.JwtConstants.JWT_SECRET;
import static com.oneline.shimpyo.security.jwt.JwtConstants.RT_EXP_TIME;

@Component
public class JwtTokenUtil {
    public static String generateToken(String username, boolean isMember, long EXP_TIME) {
            return JWT.create()
                    .withSubject(username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXP_TIME))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withClaim("isMember", isMember)
                    .withClaim("username", username)
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

    public static String generateOAuth2Token(PrincipalDetails principal, boolean isMember, long EXP_TIME) {
        return JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 10)))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("id", principal.getMember().getId())
                .withClaim("isMember", true)
                .withClaim("username", principal.getUsername())
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    // 재발급 토큰
    public static String reissuanceAccessToken(String username, boolean isMember, long EXP_TIME, long now) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(now + EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("isMember", isMember)
                .withClaim("username", username)
                .sign(HMAC256(JWT_SECRET));
    }

    public static String ressuanceRefreshToken(String username, boolean isMember, long EXP_TIME, long now) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(now + RT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("username", username)
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

    public Authentication generateAnonymousToken() {
        return null;
    }
}
