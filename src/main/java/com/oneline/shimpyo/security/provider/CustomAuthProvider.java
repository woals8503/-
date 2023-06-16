package com.oneline.shimpyo.security.provider;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.security.auth.PrincipalDetails;
import com.oneline.shimpyo.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.oneline.shimpyo.domain.BaseResponseStatus.*;

// 인증 과정을 처리
@RequiredArgsConstructor
@Component
@Slf4j
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
            throw new BaseException(BAD_CREDENTIALS_EXCEPTION);
        }

        // 인증 완료 시 완료된 Authentication 객체를 리턴
        return new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
