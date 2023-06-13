package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.dto.MemberReq;
import com.oneline.shimpyo.domain.member.dto.ResetPasswordReq;
import com.oneline.shimpyo.domain.member.dto.ResultRes;
import com.oneline.shimpyo.domain.member.dto.EmailRes;
import com.oneline.shimpyo.security.PrincipalDetails;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;

import static com.oneline.shimpyo.domain.BaseResponseStatus.*;
import static com.oneline.shimpyo.security.jwt.JwtConstants.*;
import static com.oneline.shimpyo.utils.RegexValidator.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
    public BaseResponse<Void> duplicateEmail(@RequestParam(value = "email") String email) {
        boolean check = memberService.duplicateEmail(email);
        if(!check)
            return new BaseResponse<>(EMAIL_DUPLICATE);
        return new BaseResponse<>();
    }

    //휴대폰 번호 중복 검사
    @PostMapping("/api/check-phone")
    public BaseResponse<Void> duplicatePhoneNumber(@RequestParam(value = "phoneNumber") String phoneNumber) {
        boolean check = memberService.duplicatePhoneNumber(phoneNumber);
        if(!check)
            return new BaseResponse<>(PHONE_NUMBER_DUPLICATE);
        return new BaseResponse<>();
    }

    // 닉네임 중복 검사
    @PostMapping("/api/check-nickname/{nickname}")
    public BaseResponse<Void> duplicateNickname(@RequestParam(value ="nickname") String nickname) {
        boolean check = memberService.duplicateNickname(nickname);
        if(!check) return new BaseResponse<>(NICKNAME_DUPLICATE);
        return new BaseResponse<>();
    }

    // 이메일 유무 검사
    @PostMapping("/api/check-user")
    public BaseResponse<Void> checkUser(@RequestParam(value = "email") String email) {
        Member user = memberService.checkUser(email);

        if(user == null)
            return new BaseResponse<>(MEMBER_NONEXISTENT);

        return new BaseResponse<>();
    }

    @PatchMapping("/api/reset-pwd")
    public BaseResponse<Void> resetPwd(@RequestBody ResetPasswordReq request) {
        // 이메일 정규식 표현 필요

        boolean isValid = validatePassword(request.getFirstPassword(), request.getSecondPassword());

        if(isValid)
            memberService.changePassword(request);
        else new BaseResponse<>(PASSWORD_REGEX_WRONG);

        return new BaseResponse<>();
    }

    @PostMapping("/api/certification/{phoneNumber}")
    public BaseResponse<Void> CertificationPhone(@RequestParam(value ="phoneNumber") String phoneNumber) {
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        log.info("수신자 번호 : " + phoneNumber);
        log.info("인증번호 : " + numStr);
        memberService.certifiedPhoneNumber(phoneNumber,numStr);
        return new BaseResponse<>();
    }
    
    // 인증 성공 시 이메일 보여주기
    @PostMapping("/api/show-email/{phoneNumber}")
    public BaseResponse<EmailRes> findEmail(
            @PathVariable("phoneNumber") String phoneNumber) {
        String findEmail = memberService.findByEmailWithPhonNumber(phoneNumber);

        return new BaseResponse<>(new EmailRes(findEmail));
    }

    // Access 토큰 만료 시 새로운 토큰을 발급
    @GetMapping("/api/refresh")
    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            throw new BaseException(JWT_TOKEN_NONEXISTENT);
        }
        String refreshToken = authorizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Map<String, String> tokens = memberService.refresh(refreshToken, response);
        response.setHeader(AT_HEADER, tokens.get(AT_HEADER));
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        member.getId();
        return "test";
    }

    @GetMapping("/my")
    public String my() {
        System.out.println("zzz");
        return "my";
    }

    @GetMapping("/api/admin")
    public String admin() {
        System.out.println("zzz");
        return "admin";
    }
}

