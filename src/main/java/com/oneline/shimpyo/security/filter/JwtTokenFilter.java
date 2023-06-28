package com.oneline.shimpyo.security.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.exception.ErrorResponse;
import com.oneline.shimpyo.security.auth.PrincipalDetails;
import com.oneline.shimpyo.security.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.oneline.shimpyo.security.jwt.JwtConstants.TOKEN_HEADER_PREFIX;
import static com.oneline.shimpyo.security.jwt.JwtTokenUtil.getUsernameFromToken;
import static com.oneline.shimpyo.security.jwt.JwtTokenUtil.validateToken;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class JwtTokenFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private MemberRepository memberRepository;

    private final JwtTokenUtil jwtTokenProvider;

    public JwtTokenFilter(RequestMatcher matcher, JwtTokenUtil jwtTokenProvider, AuthenticationManager authenticationManager) {
        super(matcher);
        this.jwtTokenProvider = jwtTokenProvider;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = extractToken(request);

        // 토큰이 있을경우
        if(!StringUtils.isEmpty(token)) {
            try {
                if(validateToken(token)) {
                    String username = getUsernameFromToken(token);
                    Member member = memberRepository.findByEmail(username);
                    PrincipalDetails principalDetails = new PrincipalDetails(member);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, principalDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(auth); // Security의 session에 저장
                }
                else {
                    log.info("JWT 토큰이 잘못되었습니다.");
                    response.setStatus(SC_BAD_REQUEST);
                    response.setContentType(APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("utf-8");
                    ErrorResponse errorResponse = new ErrorResponse(400, "잘못된 JWT Token 입니다.");
                    new ObjectMapper().writeValue(response.getWriter(), errorResponse);
                }
            } catch (TokenExpiredException e) {
                log.info("Access Token이 만료되었습니다.");
                response.setStatus(SC_UNAUTHORIZED);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                ErrorResponse errorResponse = new ErrorResponse(401, "Access Token이 만료되었습니다.");
                new ObjectMapper().writeValue(response.getWriter(), errorResponse);
            } catch (Exception e) {
                log.info("JWT 토큰이 잘못되었습니다. message : {}", e.getMessage());
                response.setStatus(SC_BAD_REQUEST);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                ErrorResponse errorResponse = new ErrorResponse(400, "잘못된 JWT Token 입니다.");
                new ObjectMapper().writeValue(response.getWriter(), errorResponse);
            }
        }

        // 비회원은 임시 또는 기본 토큰으로 인증 통과 가능
        if (isAnonymousUser()) {
            return jwtTokenProvider.generateAnonymousToken();
        }

        throw new BadCredentialsException("Invalid authentication token");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isAnonymousUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken;
    }
}