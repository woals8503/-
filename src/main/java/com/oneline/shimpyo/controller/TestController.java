package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.dto.SampleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
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

//    @GetMapping("/test/woals")
//    public String test3Method2() {
//        Test test = testRepository.findById(2L).get();
//        return test.getName();
//    }

}
