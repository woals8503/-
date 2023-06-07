package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationReq;
import com.oneline.shimpyo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservation")
    public BaseResponse<GetPrepareReservationReq> prepareReservation() throws BaseException {
        //todo JWT token
        return new BaseResponse<>(reservationService.prepareReservation(2L));
    }
}
