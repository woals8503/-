package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.dto.SampleDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TestController {

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

    @GetMapping("/test/woals")
    public String test3Method2() {
        return "woals";
    }

}
