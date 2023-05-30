package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

}
