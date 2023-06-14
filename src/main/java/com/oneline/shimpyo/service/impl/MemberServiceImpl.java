package com.oneline.shimpyo.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.dto.MemberReq;
import com.oneline.shimpyo.domain.member.dto.ResetPasswordReq;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.security.CustomBCryptPasswordEncoder;
import com.oneline.shimpyo.security.PrincipalDetails;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.oneline.shimpyo.security.handler.CustomSuccessHandler.createCookie;
import static com.oneline.shimpyo.security.jwt.JwtConstants.*;
import static com.oneline.shimpyo.security.jwt.JwtTokenUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;
    private final CustomBCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = false)
    public void join(MemberReq request) {
        memberRepository.save(new Member(request, bCryptPasswordEncoder));
    }

    @Override
    public boolean duplicateEmail(String email) {
        Member findEmail = memberRepository.findByEmail(email);
        if(findEmail == null) return true;  // 없으면
        return false;   // 있으면
    }


    @Override
    public Member checkUser(String email) {
        Member findUser = memberRepository.findByEmail(email);
        return findUser;
    }

    @Override
    public boolean duplicateNickname(String nickname) {
        Member findNickname = memberRepository.findByNickname(nickname);
        if(findNickname == null) return true;
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public void changePassword(ResetPasswordReq request) {
        Member findMember = memberRepository.findByMemberWithPhoneNumber(request.getPhoneNumber());
        // 더티 체킹
        findMember.resetPassword(request.getFirstPassword(), bCryptPasswordEncoder);
    }

    @Override
    public void certifiedPhoneNumber(String phoneNumber, String cerNum) {
        String api_key = "NCSLPLZK7B7SXDD2";
        String api_secret = "5BFDNWUEB6LI0ZCYUTHCKMZ6BROXND6I";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "11111111");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "인증번호는" + "["+cerNum+"]" + "입니다.");

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            log.info(obj.toString());
        } catch (CoolsmsException e) {
            log.info(e.getMessage());
            log.info(String.valueOf(e.getCode()));
        }
    }

    @Override
    public String findByEmailWithPhonNumber(String phoneNumber) {
        return memberRepository.findByEmailWithPhonNumber(phoneNumber);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateRefreshToken(String username, String refreshToken) {
        Member findMember = memberRepository.findByEmail(username);
        if(findMember == null)
            new RuntimeException("사용자를 찾을 수 없습니다.");

        findMember.updateRefreshToken(refreshToken);
    }

    @Override
    public Map<String, String> refresh(String refreshToken, HttpServletResponse response) {

        // === Refresh Token 유효성 검사 === //
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);

        // === Access Token 재발급 === //
        long now = System.currentTimeMillis();
        String username = decodedJWT.getSubject();
        Member member = memberRepository.findByEmail(username);
        if(member == null)
            new UsernameNotFoundException("사용자를 찾을 수 없습니다.");

        if (!member.getRefreshToken().equals(refreshToken)) {
            throw new JWTVerificationException("유효하지 않은 Refresh Token 입니다.");
        }

        String accessToken
                = reissuanceAccessToken(username, true, AT_EXP_TIME, now);

        Map<String, String> accessTokenResponseMap = new HashMap<>();

        // === 현재시간과 Refresh Token 만료날짜를 통해 남은 만료기간 계산 === //
        // === Refresh Token 만료시간 계산해 1개월 미만일 시 refresh token도 발급 === //
        long refreshExpireTime = decodedJWT.getClaim("exp").asLong() * 1000;
        long diffDays = (refreshExpireTime - now) / 1000 / (24 * 3600);
        long diffMin = (refreshExpireTime - now) / 1000 / 60;
        if (diffMin < 5) {
            String newRefreshToken
                    = ressuanceRefreshToken(member.getEmail(), true, RT_EXP_TIME, now);

            response.addCookie(createCookie(newRefreshToken));
//            accessTokenResponseMap.put(RT_HEADER, newRefreshToken);   refresh 토큰 body에서 제외
            member.updateRefreshToken(newRefreshToken);
        }

        accessTokenResponseMap.put(AT_HEADER, accessToken);
        return accessTokenResponseMap;
    }

    @Override
    public boolean duplicatePhoneNumber(String phoneNumber) {
        Member findMember = memberRepository.findByMemberWithPhoneNumber(phoneNumber);
        if(findMember != null)
            return false;

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username);

        return new PrincipalDetails(member);
    }

}
