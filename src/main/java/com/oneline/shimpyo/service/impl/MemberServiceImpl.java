package com.oneline.shimpyo.service.impl;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.dto.MemberReq;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = false)
    public void join(MemberReq request) {
        Member member = new Member(request, bCryptPasswordEncoder);
        memberRepository.save(member);
    }
}
