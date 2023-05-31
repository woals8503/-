package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;
}
