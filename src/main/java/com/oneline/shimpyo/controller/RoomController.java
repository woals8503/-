package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
}
