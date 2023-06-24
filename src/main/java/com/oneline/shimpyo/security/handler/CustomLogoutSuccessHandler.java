package com.oneline.shimpyo.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.security.jwt.JwtService;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.TypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.oneline.shimpyo.domain.BaseResponseStatus.JWT_TOKEN_WRONG;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    // logoutHandler는 예외가 발생하지 않는다 -> 처리기 중 일부 캐시를 정리를 수행할 수 있기 때문에 해당 메서드는 성공적으로 완료되어야한다.
    private final MemberService memberService;
    private final JwtService jwtService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Long memberId = jwtService.getMemberId();
        memberService.removeRefreshToken(memberId);
        log.info("refresh 토큰 삭제 완료");

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        BaseResponse<Void> baseResponse = new BaseResponse<>();
        
        new ObjectMapper().writeValue(response.getWriter(), baseResponse);
    }
}
