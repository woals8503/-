package com.oneline.shimpyo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/data")
    public String testMethod(){
        return "Completed??";
    }

    @GetMapping("/test/data3")
    public String test3Method(){
        return "테스트 성공!";
    }

    @GetMapping("/test/woals")
    public String test3Method2(){
        return "woals";
    }

}
