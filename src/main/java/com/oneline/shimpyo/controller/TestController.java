package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.dto.SampleDto;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class TestController {

    private final MemberRepository memberRepository;

    @GetMapping("/test")
    public BaseResponse<SampleDto> sample(){
        return new BaseResponse<>(new SampleDto("first JSON Test Data", "second JSON Test Data"));
    }

    @GetMapping("/test/data")
    public String testMethod() {
        return "Completed??";
    }

    @GetMapping("/test/data3")
    public String test3Method() {
        return "테스트 성공!";
    }

    @GetMapping("/test/data4")
    public String test4Method() {
        List<Member> all = memberRepository.findAll();
        if(all.isEmpty()) {
            System.out.println("ㅇㅇ");
        }
        return "ok";
    }
//    @GetMapping("/test/woals")
//    public String test3Method2() {
//        Test test = testRepository.findById(2L).get();
//        return test.getName();
//    }

}
