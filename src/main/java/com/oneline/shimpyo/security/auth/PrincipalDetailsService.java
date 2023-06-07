package com.oneline.shimpyo.security.auth;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    // Security session { Authentication(내부 UserDetails) }
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // html에서 이름을 username으로 통일해야함  -> name="username2" 라고 지정하면 동작 x
        Member member = memberRepository.findByEmail(username);
        if(member != null) {
            return new PrincipalDetails(member);
        }
        return null;
    }
}
