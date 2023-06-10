package com.oneline.shimpyo.security;

import com.oneline.shimpyo.service.MemberService;
import com.oneline.shimpyo.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// 인증 과정을 처리
@RequiredArgsConstructor
@Component
public class CustomAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final MemberServiceImpl memberService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        PrincipalDetails principalDetails = (PrincipalDetails) memberService.loadUserByUsername(username);


        // PW 검사
        if (!passwordEncoder.matches(password, principalDetails.getPassword())) {
            throw new BadCredentialsException("Provider - authenticate() : 비밀번호가 일치하지 않습니다.");
        }
    
        // 인증 완료 시 완료된 Authentication 객체를 리턴
        return new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
