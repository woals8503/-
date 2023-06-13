package com.oneline.shimpyo.security.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.exception.ErrorResponse;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.security.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static com.oneline.shimpyo.security.jwt.JwtConstants.*;
import static com.oneline.shimpyo.security.jwt.JwtTokenUtil.*;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("권한 필터");
        String servletPath = request.getServletPath();
        String authrizationHeader = request.getHeader(AUTHORIZATION);
        log.info(servletPath);
        // 로그인, 회원가입, 토큰 재발급의 url일 경우 토큰 검증 x
        if (servletPath.equals("/")
                || servletPath.equals("/api/login")
                || servletPath.equals("/api/refresh")
                || servletPath.equals("/api/join")
                || servletPath.equals("/api/check-email/")
        ) {
            filterChain.doFilter(request, response);
        }

        else if (authrizationHeader == null) {
            // 비회원이면서 header에 토큰값이 없을 경우 비회원 전용 토큰 생성
            if(!nonMemberCheck(request)) {
                String nonMemberToken = generateToken("non_member", false, NON_MEMBER_EXPIRATION_TIME);
                log.info("비회원 Access 토큰이 발급되었습니다.");
                Map<String, String> responseMap = new HashMap<>();
                responseMap.put(AT_HEADER, nonMemberToken);
                new ObjectMapper().writeValue(response.getWriter(), responseMap);
            }
            // 비회원도 아닌데 토큰값이 없다면 Error Response
            else {
                log.info("JWT Token이 존재하지 않습니다.");
                response.setStatus(SC_BAD_REQUEST);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                ErrorResponse errorResponse = new ErrorResponse(400, "JWT Token이 존재하지 않습니다.");
                new ObjectMapper().writeValue(response.getWriter(), errorResponse);
            }
        }
        // JWT 토큰 값이 Bearer로 시작하지 않는다면
        else if(!authrizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            // 잘못된 토큰값이라면
            log.info("JWT 토큰이 잘못되었습니다.");
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            ErrorResponse errorResponse = new ErrorResponse(400, "잘못된 JWT Token 입니다.");
            new ObjectMapper().writeValue(response.getWriter(), errorResponse);
        }
        // 올바른 규정의 토큰 값이라면 ( EX : Bearer ey.... )
        else {
            try {
                String accessToken = request.getHeader(AUTHORIZATION).replace(TOKEN_HEADER_PREFIX, "");
                String username = null;

                // 회원 & 비회원 체크
                boolean check = isMember(accessToken);

                // 회원일 경우
                if(check) {
                    username = getUsernameFromToken(accessToken);
                    if(username != null) {
                        Member member = memberRepository.findByEmail(username);
                        PrincipalDetails principalDetails = new PrincipalDetails(member);

                        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username,null, principalDetails.getAuthorities());
                        //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
                // 비회원일 경우
                else {
                    username = getUsernameFromToken(accessToken);
                    if(username != null) {
                        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username,null, null);
                        //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
                filterChain.doFilter(request, response);
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
    }

    private boolean nonMemberCheck(HttpServletRequest request) {
        if(request.getAttribute("username") != null)
            return true;
        else
            return false;
    }
}