package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
}
