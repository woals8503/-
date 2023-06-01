package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;
}
