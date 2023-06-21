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

import static com.oneline.shimpyo.security.handler.CustomSuccessHandler.createCookie;
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
        Member member = principal.getMember();

        // 이미 가입한 유저라면
        if(member.getSocial()) {
            // 리다이렉트 타겟 url 생성 ( 로그인 성공 시 리다이렉트 URL )
            String targetUrl;
            targetUrl = UriComponentsBuilder.fromUriString("/api/test5")    // -> 메인페이지
                    .build().toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
        // 회원 x
        else {
            log.info("추가정보를 입력받아야합니다.");

            //
            //
            BaseResponse<Void> mapBaseResponse = new BaseResponse<>();
            new ObjectMapper().writeValue(response.getWriter(), mapBaseResponse);

            // 리다이렉트 url 생성 ( 메인페이지로 됬으면 좋겠음 )
//            String targetUrl;
//            targetUrl = UriComponentsBuilder.fromUriString("/api/test4")    // -> 추가 회원가입 창
//                    .queryParam("memberId", member.getId())
//                    .build().toUriString();
//            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }

    }

}
