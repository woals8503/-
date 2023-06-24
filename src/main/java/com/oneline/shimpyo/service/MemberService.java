package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.UpdateMemberReq;
import com.oneline.shimpyo.domain.member.dto.EmailRes;
import com.oneline.shimpyo.domain.member.dto.MemberReq;
import com.oneline.shimpyo.domain.member.dto.OAuthInfoReq;
import com.oneline.shimpyo.domain.member.dto.ResetPasswordReq;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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

    void updateRefreshToken(String username, String refreshToken);

    Map<String, String> refresh(String refreshToken, HttpServletResponse response);

    boolean duplicatePhoneNumber(String phoneNumber);

    void removeRefreshToken(Long id);

    void oauthJoin(OAuthInfoReq oAuthInfoReq);

    void updateMember(Member member, UpdateMemberReq memberReq);

    void removeMember(Member member);

}
