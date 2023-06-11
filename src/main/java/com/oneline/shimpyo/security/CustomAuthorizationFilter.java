package com.oneline.shimpyo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.exception.ErrorResponse;
import com.oneline.shimpyo.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.oneline.shimpyo.security.JwtConstants.*;
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
        String servletPath = request.getServletPath();
        String authrizationHeader = request.getHeader(AUTHORIZATION);
        // 해당 url이라면 토큰 검사하지 않고 진행
//        if (servletPath.equals("/api/login") || servletPath.equals("/api/refresh")) {
//            System.out.println("로그인 진행");
//            filterChain.doFilter(request, response);
//        }

        // Header에 토큰값이 없다면 검증 실패로 다시 필터를 타게됨
        if (authrizationHeader == null || !authrizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            // 토큰값이 없거나 정상적이지 않다면 400 오류
//            log.info("CustomAuthorizationFilter : JWT Token이 존재하지 않습니다.");
//            response.setStatus(SC_BAD_REQUEST);
//            response.setContentType(APPLICATION_JSON_VALUE);
//            response.setCharacterEncoding("utf-8");
//            ErrorResponse errorResponse = new ErrorResponse(400, "JWT Token이 존재하지 않습니다.");
//            new ObjectMapper().writeValue(response.getWriter(), errorResponse);
            filterChain.doFilter(request, response);
            return;
        }

        
        // JWT 토큰 검증을 해서 정상적인 사용자인지 확인
        try {
            // Access Token만 꺼내옴
            String accessToken = request.getHeader(AUTHORIZATION).replace(TOKEN_HEADER_PREFIX, "");
            System.out.println(accessToken);
// === Access Token 검증 === //
//            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
//            DecodedJWT decodedJWT = verifier.verify(accessToken);
// === Access Token 내 Claim에서 Authorities 꺼내 Authentication 객체 생성 & SecurityContext에 저장 === //
//                List<String> strAuthorities = decodedJWT.getClaim("roles").asList(String.class);
//                List<SimpleGrantedAuthority> authorities = strAuthorities.stream().map(SimpleGrantedAuthority::new).toList();
//            String username = decodedJWT.getSubject();

            String username =
                    JWT.require(Algorithm.HMAC256(JWT_SECRET)).build()
                            .verify(accessToken)
                            .getClaim("username")
                            .asString();
            System.out.println("유저 이름은? : " + username);

            if(username != null) {
                Member member = memberRepository.findByEmail(username);
                PrincipalDetails principalDetails = new PrincipalDetails(member);

                System.out.println("권한 부여 없음 : " + principalDetails.getAuthorities());

                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username,null, principalDetails.getAuthorities());
                //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            log.info("CustomAuthorizationFilter : Access Token이 만료되었습니다.");
            response.setStatus(SC_UNAUTHORIZED);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            ErrorResponse errorResponse = new ErrorResponse(401, "Access Token이 만료되었습니다.");
            new ObjectMapper().writeValue(response.getWriter(), errorResponse);
        } catch (Exception e) {
            log.info("CustomAuthorizationFilter : JWT 토큰이 잘못되었습니다. message : {}", e.getMessage());
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            ErrorResponse errorResponse = new ErrorResponse(400, "잘못된 JWT Token 입니다.");
            new ObjectMapper().writeValue(response.getWriter(), errorResponse);
        }

    }
}
