package com.oneline.shimpyo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.house.HouseType;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.dto.*;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.security.auth.CurrentMember;
import com.oneline.shimpyo.security.auth.PrincipalDetails;
import com.oneline.shimpyo.security.jwt.JwtService;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.oneline.shimpyo.domain.BaseResponseStatus.*;
import static com.oneline.shimpyo.domain.house.HouseType.*;
import static com.oneline.shimpyo.domain.house.HouseType.MOTEL;
import static com.oneline.shimpyo.modules.RegexValidator.*;
import static com.oneline.shimpyo.security.handler.CustomSuccessHandler.createCookie;
import static com.oneline.shimpyo.security.jwt.JwtConstants.*;
import static com.oneline.shimpyo.security.jwt.JwtTokenUtil.generateOAuth2Token;
import static com.oneline.shimpyo.security.jwt.JwtTokenUtil.generateToken;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @PostMapping("/api/join")
    public BaseResponse<Void> join(@RequestBody MemberReq memberReq) {

        boolean isValid = validateRequest(memberReq);
        if(!isValid)
            return new BaseResponse<>(MEMBER_REGEX_WRONG);

        memberService.join(memberReq);
        return new BaseResponse<>();
    }

    @PostMapping("/api/oauth-join")
    public BaseResponse<Void> oauthJoin(@RequestBody OAuthInfoReq oAuthInfoReq) {

        boolean isValid = validateOAuthRequest(oAuthInfoReq);

        if(!isValid)
            return new BaseResponse<>(MEMBER_REGEX_WRONG);

        memberService.oauthJoin(oAuthInfoReq);
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

    // 추가 회원가입 창 리다이렉트`
    @GetMapping("/api/")
    public BaseResponse<Map<String, String>> test4 (
            @RequestParam("memberId") Long memberId,
            HttpServletResponse response) {
        Member member = memberRepository.findById(memberId).get();

        // 토큰 생성
        String accessToken = generateOAuth2Token(member, true, AT_EXP_TIME);
        String refreshToken = generateOAuth2Token(member, true, RT_EXP_TIME);

        // 토큰 저장
        memberService.updateRefreshToken(member.getEmail(), refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.addHeader("Set-Cookie", createCookie(refreshToken).toString());

        // 헤더 생성
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(AT_HEADER, accessToken);

        return new BaseResponse<>(responseMap);
    }

//    @GetMapping("/google")
//    public String googleOAuthRedirect(@RequestParam String code) {
//        RestTemplate rt = new RestTemplate();
//
//        HttpHead headers = new HttpHead();
//
//        headers.addHeader("Content-Type", "application/x-www-form-urlencoded");
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("client_id", "479304112287-brdp845tmalh8o7obogv81hi1387pso8.apps.googleusercontent.com");
//        params.add("client_secret", "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-ZN");
//        params.add("code", code);
//        params.add("grant_type", "authorization_code");
//        params.add("redirect_uri", "http://shimpyo-api.p-e.kr:8081/login/oauth2/code/google");
//
//        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, (MultiValueMap<String, String>) headers);
//
//        ResponseEntity<String> accessTokenResponse = rt.exchange(
//                "https://oauth2.googleapis.com/token",
//                HttpMethod.POST,
//                accessTokenRequest,
//                String.class
//        );
//
//        // 여기서부터, 프로필 정보 얻어오는 요청
//        HttpHeaders headers1 = new HttpHeaders();
//        headers1.add("Authorization", "Bearer " + googleOauthParams.getAccess_token());
//
//        HttpEntity profileRequest = new HttpEntity(headers1);
//
//        ResponseEntity<String> profileResponse = rt.exchange(
//                "https://oauth2.googleapis.com/tokeninfo?id_token=" + googleOauthParams.getId_token(),
//                HttpMethod.GET,
//                profileRequest,
//                String.class
//        );
//
//        return "프로필 정보 : " + profileResponse.getBody();
//    }

    @GetMapping("/api/test3")
    public String test3(@CurrentMember Member member) {
        return "test";
    }

    @GetMapping("/api/test5")
    public String test5() {
        return "test";
    }

}