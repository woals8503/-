package com.oneline.shimpyo.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

import static com.oneline.shimpyo.security.jwt.JwtConstants.JWT_SECRET;
import static com.oneline.shimpyo.security.jwt.JwtConstants.RT_EXP_TIME;

public class JwtTokenUtil {
    public static String generateToken(String username, boolean isMember, long EXP_TIME) {
            return JWT.create()
                    .withSubject(username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXP_TIME))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withClaim("isMember", isMember)
                    .withClaim("username", username)
                    .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    // 재발급 토큰
    public static String reissuanceAccessToken(String username, boolean isMember, long EXP_TIME, long now) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(now + EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("isMember", isMember)
                .withClaim("username", username)
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    public static String ressuanceRefreshToken(String username, boolean isMember, long EXP_TIME, long now) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(now + RT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("username", username)
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    // 토큰 확인
    public static boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(JWT_SECRET)).build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 회원인지 아닌지
    public static boolean isMember(String token) {
        return JWT.require(Algorithm.HMAC256(JWT_SECRET)).build()
                .verify(token)
                .getClaim("isMember")
                .asBoolean();
    }

    public static String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(JWT_SECRET)).build()
                .verify(token)
                .getClaim("username")
                .asString();
    }
}
