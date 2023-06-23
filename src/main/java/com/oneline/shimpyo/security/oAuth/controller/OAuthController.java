package com.oneline.shimpyo.security.oAuth.controller;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.security.auth.CurrentMember;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
    @GetMapping("/loginInfo")
    public String oauthLoginInfo(@CurrentMember Member member){
        System.out.println(member.getEmail());
        //oAuth2User.toString() 예시 : Name: [2346930276], Granted Authorities: [[USER]], User Attributes: [{id=2346930276, provider=kakao, name=김준우, email=bababoll@naver.com}]
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        //attributes.toString() 예시 : {id=2346930276, provider=kakao, name=김준우, email=bababoll@naver.com}
//        Map<String, Object> attributes = oAuth2User.getAttributes();

        return "ok";
    }
}
