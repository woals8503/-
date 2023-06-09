package com.oneline.shimpyo.service.impl;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.dto.MemberReq;
import com.oneline.shimpyo.domain.member.dto.ResetPasswordReq;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = false)
    public void join(MemberReq request) {
        memberRepository.save(new Member(request, bCryptPasswordEncoder));
    }

    @Override
    public boolean duplicateEmail(String email) {
        Member findEmail = memberRepository.findByEmail(email);
        if(findEmail == null) return false;
        return true;
    }

    @Override
    public Member checkUser(String email) {
        Member findUser = memberRepository.findByEmail(email);
        return findUser;
    }

    @Override
    public boolean duplicateNickname(String nickname) {
        Member findNickname = memberRepository.findByNickname(nickname);
        if(findNickname == null) return false;
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public void changePassword(ResetPasswordReq request) {
        Member findMember = memberRepository.findByMemberWithPhoneNumber(request.getPhoneNumber());
        // 더티 체킹
        findMember.resetPassword(request.getPassword());
    }

    @Override
    public void certifiedPhoneNumber(String phoneNumber, String cerNum) {
        String api_key = "본인의 API KEY";
        String api_secret = "본인의 API SECRET";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "발송할 번호 입력");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "핫띵크 휴대폰인증 테스트 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }

    @Override
    public String findByEmailWithPhonNumber(String phoneNumber) {
        return memberRepository.findByEmailWithPhonNumber(phoneNumber);
    }

}
