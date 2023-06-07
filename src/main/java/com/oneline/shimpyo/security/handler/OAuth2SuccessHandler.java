package com.oneline.shimpyo.security.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.oneline.shimpyo.security.auth.PrincipalDetails;
import com.oneline.shimpyo.security.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        System.out.println("성공");
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        //JWT 토큰 생성
        String jwtToken = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 10)))    // 토큰 만료시간 설정 ( 10분 )
                .withClaim("id", principal.getMember().getId())
                .withClaim("username", principal.getMember().getEmail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        //리다이렉트 타겟 url 생성
        String targetUrl;
        targetUrl = UriComponentsBuilder.fromUriString("/").build().toUriString();
        System.out.println("targetUrl : " +targetUrl);
        System.out.println("jwtToken : " + jwtToken);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
