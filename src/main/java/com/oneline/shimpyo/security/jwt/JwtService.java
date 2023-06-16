package com.oneline.shimpyo.security.jwt;

import com.oneline.shimpyo.domain.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.oneline.shimpyo.domain.BaseResponseStatus.JWT_TOKEN_INVALID;
import static com.oneline.shimpyo.domain.BaseResponseStatus.JWT_TOKEN_NONEXISTENT;
import static com.oneline.shimpyo.security.jwt.JwtTokenUtil.getIdFromToken;
import static com.oneline.shimpyo.security.jwt.JwtTokenUtil.validateToken;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class JwtService {

    // 헤더에서 토큰 값 꺼내기
    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader(AUTHORIZATION);
    }

    public Long getMemberId() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt().substring(7);
        if (accessToken == null || accessToken.length() == 0) {
            throw new BaseException(JWT_TOKEN_NONEXISTENT);
        }

        // 2. JWT parsing
        try {
            validateToken(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(JWT_TOKEN_INVALID);
        }

        Long memberId;
        try {
            memberId = getIdFromToken(accessToken);
        } catch (NullPointerException e) {
            throw new BaseException(JWT_TOKEN_INVALID);
        }

        // 3. userIdx 추출
        return memberId;
    }

    public void checkJwtByStoreId(long memberId) throws BaseException {
        long userIdxByJwt = getMemberId();
        
        if (memberId != userIdxByJwt) {
            throw new BaseException(JWT_TOKEN_INVALID);
        }

    }
}