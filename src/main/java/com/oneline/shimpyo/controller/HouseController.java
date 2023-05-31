package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;
}
