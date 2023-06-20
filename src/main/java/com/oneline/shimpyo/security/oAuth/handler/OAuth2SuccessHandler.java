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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.oneline.shimpyo.security.jwt.JwtConstants.*;
import static com.oneline.shimpyo.security.jwt.JwtTokenUtil.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

        Member member = memberRepository.findByEmail(principal.getMember().getEmail());

        // 이미 가입한 유저라면
        if(member != null) {
            String accessToken = generateToken(principal, true, AT_EXP_TIME);
            String refreshToken = generateToken(principal, true, RT_EXP_TIME);

            memberService.updateRefreshToken(principal.getMember().getEmail(), refreshToken);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            response.addCookie(createCookie(refreshToken));

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put(AT_HEADER, accessToken);
//            BaseResponse<Map<String, String>> mapBaseResponse = new BaseResponse<>(responseMap);
//            new ObjectMapper().writeValue(response.getWriter(), mapBaseResponse);

            // 리다이렉트 타겟 url 생성 ( 로그인 성공 시 리다이렉트 URL )
            String targetUrl;
            targetUrl = UriComponentsBuilder.fromUriString("/api/test4").build().toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }

        // 회원가입이 필요한 사용자라면
        else {
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("email", principal.getUsername());
            responseMap.put("nickname", principal.getMember().getNickname());
            responseMap.put("provider", principal.getMember().getProvider());
            responseMap.put("providerId", principal.getMember().getProviderId());
//            BaseResponse<Map<String, String>> mapBaseResponse = new BaseResponse<>(responseMap);
//            new ObjectMapper().writeValue(response.getWriter(), mapBaseResponse);
            
            // 메인페이지로 이동
            String targetUrl;
            targetUrl = UriComponentsBuilder.fromUriString("/api/test5").build().toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }

    }

    private Cookie createCookie(String refreshToken) {
        Cookie cookie = new Cookie(RT_HEADER, refreshToken);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }
}
