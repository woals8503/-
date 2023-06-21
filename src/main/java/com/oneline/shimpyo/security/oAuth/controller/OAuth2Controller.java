package com.oneline.shimpyo.security.oAuth.controller;

import com.oneline.shimpyo.security.oAuth.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

    @GetMapping("/code/{registrationId}")
    public void oAuth2Login(@RequestParam String code, @PathVariable String registrationId) {
        System.out.println("code : " + code);
        System.out.println("registrationId : " + registrationId);
        oAuth2Service.socailLogin(code, registrationId);
    }
}
