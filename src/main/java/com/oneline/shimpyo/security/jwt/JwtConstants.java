package com.oneline.shimpyo.security.jwt;

public class JwtConstants {
    // Expiration Time
    public static final long MINUTE = 1000 * 60;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long MONTH = 30 * DAY;

    public static final long AT_EXP_TIME_NONE = 10 * MINUTE;
    public static final long AT_EXP_TIME =  100 * MINUTE;
    public static final long RT_EXP_TIME =  1000 * MINUTE;
    public static final long NON_MEMBER_EXP_TIME = MINUTE;

    // Secret
    public static final String JWT_SECRET = "cos";

    // Header
    public static final String AT_HEADER = "accessToken";
    public static final String RT_HEADER = "refreshToken";
    public static final String TOKEN_HEADER_PREFIX = "Bearer ";
    public static final long NON_MEMBER_EXPIRATION_TIME = 86400000; // 비회원 24 시간
}
