package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.dto.MemberReq;
import com.oneline.shimpyo.domain.member.dto.ResetPasswordReq;

public interface MemberService {
    // 회원가입
    void join(MemberReq member);

    // 이메일 중복체크
    boolean duplicateEmail(String email);

    // 본인인증 ( 이메일 )
    Member checkUser(String email);
    
    // 닉네임 중복체크
    boolean duplicateNickname(String nickname);

    void changePassword(ResetPasswordReq password);

    void certifiedPhoneNumber(String phoneNumber, String numStr);

    String findByEmailWithPhonNumber(String phoneNumber);
}
