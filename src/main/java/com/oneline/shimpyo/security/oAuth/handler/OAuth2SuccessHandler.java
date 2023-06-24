package com.oneline.shimpyo.security.oAuth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.security.auth.PrincipalDetails;
import com.oneline.shimpyo.security.jwt.JwtConstants;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.oneline.shimpyo.security.handler.CustomSuccessHandler.createCookie;
import static com.oneline.shimpyo.security.jwt.JwtConstants.*;
import static com.oneline.shimpyo.security.jwt.JwtTokenUtil.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REDIRECT_URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        Member member = principal.getMember();
        // 회원이라면 메인페이지 이동
        if(member.getSocial()) {

            String accessToken = generateToken(principal, true, AT_EXP_TIME);
            String refreshToken = generateRefreshToken(principal, true, RT_EXP_TIME);

            response.sendRedirect(UriComponentsBuilder.fromUriString("/api/test5")
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .queryParam("email", member.getEmail())
                    .queryParam("additional_info", true)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString());
        }

        // 최초 로그인이라면 회원가입 창 redirect
        else {
            response.sendRedirect(UriComponentsBuilder.fromUriString("http://shimpyo.o-r.kr/")
                    .queryParam("additional_info", false)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString());
        }
    }
}
