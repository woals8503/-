package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.dto.*;
import com.oneline.shimpyo.security.auth.CurrentMember;
import com.oneline.shimpyo.security.jwt.JwtService;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;

import static com.oneline.shimpyo.domain.BaseResponseStatus.*;
import static com.oneline.shimpyo.modules.RegexValidator.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("/api/join")
    public BaseResponse<Void> join(@RequestBody MemberReq memberReq) {

        boolean isValid = validateRequest(memberReq);
        if(!isValid)
            return new BaseResponse<>(MEMBER_REGEX_WRONG);

        memberService.join(memberReq);
        return new BaseResponse<>();
    }

    //이메일 중복 검사
    @PostMapping("/api/check-email")
    public BaseResponse<Void> duplicateEmail(@RequestBody EmailReq request) {
        boolean check = memberService.duplicateEmail(request.getEmail());
        if(!check)
            return new BaseResponse<>(EMAIL_DUPLICATE);
        return new BaseResponse<>();
    }

    //휴대폰 번호 중복 검사
    @PostMapping("/api/check-phone")
    public BaseResponse<Void> duplicatePhoneNumber(@RequestBody DuplicatePhoneReq request) {
        boolean check = memberService.duplicatePhoneNumber(request.getPhoneNumber());
        if(!check)
            return new BaseResponse<>(PHONE_NUMBER_DUPLICATE);
        return new BaseResponse<>();
    }

    // 닉네임 중복 검사
    @PostMapping("/api/check-nickname")
    public BaseResponse<Void> duplicateNickname(@RequestParam(value ="nickname") String nickname) {
        boolean check = memberService.duplicateNickname(nickname);
        if(!check) return new BaseResponse<>(NICKNAME_DUPLICATE);
        return new BaseResponse<>();
    }

    // 이메일 유무 검사
    @PostMapping("/api/check-user")
    public BaseResponse<EmailRes> checkUser(@RequestBody EmailReq emailReq) {

        Member user = memberService.checkUser(emailReq.getEmail());

        if(user == null)
            return new BaseResponse<>(MEMBER_NONEXISTENT);

        return new BaseResponse<>(new EmailRes(user.getEmail()));
    }

    @PatchMapping("/api/reset-pwd")
    public BaseResponse<Void> resetPwd(@RequestBody ResetPasswordReq request) {
        // 이메일 정규식 표현 필요
        boolean isValid = validatePassword(request.getPassword());

        if(isValid)
            memberService.changePassword(request);
        else new BaseResponse<>(PASSWORD_REGEX_WRONG);

        return new BaseResponse<>();
    }

    @PostMapping("/api/certification")
    public BaseResponse<CertificationPhoneNumberRes> CertificationPhone(
            @RequestBody CertificationPhoneNumberReq request) {

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        log.info("수신자 번호 : " + request.getPhoneNumber());
        log.info("인증번호 : " + numStr);
        memberService.certifiedPhoneNumber(request.getPhoneNumber(),numStr);

        return new BaseResponse<>(new CertificationPhoneNumberRes(numStr));
    }

    // 이메일 찾기
    @PostMapping("/api/show-email")
    public BaseResponse<EmailRes> findEmail(
            @RequestBody FindEmailReq request) {
        String findEmail = memberService.findByEmailWithPhonNumber(request.getPhoneNumber());

        return new BaseResponse<>(new EmailRes(findEmail));
    }

    // Access 토큰 만료 시 새로운 토큰을 발급
    @GetMapping("/user/refresh")
    public BaseResponse<Map<String, String>> refresh(HttpServletRequest request, HttpServletResponse response, @CurrentMember Member member) {
        Cookie[] cookies = request.getCookies();
        String name = null;
        String value = null;
        for (Cookie cookie : cookies) {
            name = cookie.getName();
            value = cookie.getValue();
        }
        String refreshToken = value;

        Map<String, String> tokens = memberService.refresh(refreshToken, response);
        return new BaseResponse<>(tokens);
    }

    @GetMapping("/public/test4")
    public String test4() {
        Long memberId = jwtService.getMemberId();
        if(memberId == null) {
            System.out.println("비회원");
        }
        else System.out.println("회원");
        return "ok";
    }

    @GetMapping("/api/test3")
    public String test3(@CurrentMember Member member) {
        if(member != null)
            System.out.println("회원");

        else
            System.out.println("비회원");
        return "test";
    }

    @GetMapping("/mypage/test")
    public String test5() {

        return "test";
    }

}