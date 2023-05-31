package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
}
