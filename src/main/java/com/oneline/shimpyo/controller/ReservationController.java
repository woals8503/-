package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationRes;
import com.oneline.shimpyo.service.PaymentService;
import com.oneline.shimpyo.service.ReservationService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final PaymentService paymentService;

    @GetMapping("")
    public BaseResponse<GetPrepareReservationReq> prepareReservation() throws BaseException {
        //todo JWT token
        return new BaseResponse<>(reservationService.prepareReservation(2L));
    }

    @PostMapping("")
    public BaseResponse<PostReservationRes> createReservation(@RequestBody PostReservationReq postReservationReq)
            throws IamportResponseException, BaseException, IOException {
        //todo JWT token
        long paymentId = paymentService.createPayment(2L, postReservationReq);
        long reservationId = reservationService.createReservation(2L, paymentId, postReservationReq);

        return new BaseResponse<>(new PostReservationRes(reservationId));
    }

}
