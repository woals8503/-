package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.member.dto.MemberReq;
import com.oneline.shimpyo.security.auth.PrincipalDetails;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/join")
    public String join(@RequestBody MemberReq member) {
        memberService.join(member);
        return "회원가입 성공";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails.getMember() = " + principalDetails.getMember());
        return "user";
    }

}
