package com.oneline.shimpyo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.oneline.shimpyo.security.JwtConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        PrincipalDetails member = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(member.getUsername());

        String accessToken = JWT.create()
                .withSubject(member.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + AT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256(JWT_SECRET));
        String refreshToken = JWT.create()
                .withSubject(member.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + RT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256(JWT_SECRET));

        // Refresh Token DB에 저장
        memberService.updateRefreshToken(member.getUsername(), refreshToken);

        // Access Token , Refresh Token 프론트 단에 Response Header로 전달
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setHeader(AT_HEADER, accessToken);
        response.setHeader(RT_HEADER, refreshToken);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(AT_HEADER, accessToken);
        responseMap.put(RT_HEADER, refreshToken);
        new ObjectMapper().writeValue(response.getWriter(), responseMap);
    }
}
