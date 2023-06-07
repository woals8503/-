package com.oneline.shimpyo.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final IamportClient iamportClient;

    public PaymentController(){
        this.iamportClient = new IamportClient("6354023824364652",
                "oCGC7lJGsgCzkJG6i9JyIKwU1MtNE5SBJ7GnkCVqVzxbqaLo3DxIY5CwUQAoq5kqxsCXo2iQS4v2oeu6");
    }



}
