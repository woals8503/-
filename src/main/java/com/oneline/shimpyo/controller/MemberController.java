package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.dto.MemberReq;
import com.oneline.shimpyo.domain.member.dto.ResetPasswordReq;
import com.oneline.shimpyo.domain.member.responsebody.Result;
import com.oneline.shimpyo.domain.member.responsebody.ResultData;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.oneline.shimpyo.security.JwtConstants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/join")
    public ResponseEntity<Result<Map<String, Object>>> join(@RequestBody MemberReq member) {
        memberService.join(member);
        return ResponseEntity.ok()
                .body(new Result<>(true, 1000, "요청에 성공하였습니다."));
    }
    
    //이메일 중복검사
    @GetMapping("/api/check-email/{email}")
    public ResponseEntity<Result<Map<String, Object>>> duplicateEmail(@PathVariable("email") String email) {
        boolean check = memberService.duplicateEmail(email);
        if(!check)
            return ResponseEntity.ok()
                    .body(new Result<>(false, 2004, "요청에 실패하였습니다."));
        return ResponseEntity.ok()
                .body(new Result<>(true, 1000, "요청에 성공하였습니다."));
    }

    // 닉네임 중복검사
    @GetMapping("/api/check-nickname/{nickname}")
    public ResponseEntity<Result<Map<String, Object>>> duplicateNickname(@PathVariable("nickname") String nickname) {
        boolean check = memberService.duplicateNickname(nickname);
        if(!check) return ResponseEntity.ok()
                .body(new Result<>(false, 2004, "요청에 실패하였습니다."));
        return ResponseEntity.ok()
                .body(new Result<>(true, 1000, "요청에 성공하였습니다."));
    }

    // 비밀번호 찾기 시 이메일 유무 검사
    @GetMapping("/api/check-user/{email}")
    public ResponseEntity<Map<String, Object>> checkUser(@PathVariable("email") String email) {
        Member user = memberService.checkUser(email);
        Map<String, Object> result = new HashMap<>();

        if(user == null) {
            result.put("isSuccess", false);
            result.put("code", 2004);
            result.put("message", "요청에 실패하였습니다.");
            result.put("email", "");
            return ResponseEntity.ok().body(result);
        }

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        memberService.certifiedPhoneNumber(user.getPhoneNumber(), numStr);

        result.put("isSuccess", true);
        result.put("code", 1000);
        result.put("message", "요청에 성공하였습니다.");
        result.put("email", user);
        result.put("certificationCode", numStr);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/api/resetpwd")
    public ResponseEntity<Result<Map<String, Object>>> resetPwd(@RequestBody ResetPasswordReq request) {

        memberService.changePassword(request);

        return ResponseEntity.ok().body(new Result<>(true, 1000, "요청에 성공하였습니다."));
    }
//
//    @GetMapping("/user")
//    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        System.out.println("principalDetails.getMember() = " + principalDetails.getMember());
//        return "user";
//    }

    //이메일 찾기 시 휴대폰 인증
    @GetMapping("/api/{phoneNumber}")
    public String sendSMS(@PathVariable("phoneNumber") String phoneNumber) {
        
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        System.out.println("수신자 번호 : " + phoneNumber);
        System.out.println("인증번호 : " + numStr);
        memberService.certifiedPhoneNumber(phoneNumber,numStr);
        return numStr;
    }

    // 인증 성공 시 이메일 보여주기
    @GetMapping("/api/show-email/{phoneNumber}")
    public ResponseEntity<ResultData<Map<String, Object>>>
    findEmail(@PathVariable("phoneNumber") String phoneNumber) {
        String findEmail = memberService.findByEmailWithPhonNumber(phoneNumber);
        return ResponseEntity.ok()
                .body(new ResultData<>(true, 1000, "요청에 성공하였습니다.", findEmail));
    }

    // Access 토큰 만료 시 새로운 토큰을 발급하기 위함
    @GetMapping("/api/refresh")
    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }
        String refreshToken = authorizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        Map<String, String> tokens = memberService.refresh(refreshToken);
        response.setHeader(AT_HEADER, tokens.get(AT_HEADER));
        if (tokens.get(RT_HEADER) != null) {
            response.setHeader(RT_HEADER, tokens.get(RT_HEADER));
        }
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/my")
    public ResponseEntity<String> my() {
        return ResponseEntity.ok("My");
    }
}

