package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationRes;
import com.oneline.shimpyo.domain.reservation.dto.PatchReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationRes;
import com.oneline.shimpyo.security.jwt.JwtService;
import com.oneline.shimpyo.service.ReservationService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final JwtService jwtService;
    private final ReservationService reservationService;

    @GetMapping("")
    public BaseResponse<GetPrepareReservationRes> prepareReservation() throws BaseException {
        return new BaseResponse<>(reservationService.prepareReservation(jwtService.getMemberId()));
    }

    @PostMapping("")
    public BaseResponse<PostReservationRes> createReservation(@RequestBody PostReservationReq postReservationReq)
            throws IamportResponseException, BaseException, IOException {

        long reservationId = reservationService.createReservation(jwtService.getMemberId(), postReservationReq);
        return new BaseResponse<>(new PostReservationRes(reservationId));
    }

    @PatchMapping("/{reservationId}")
    public BaseResponse<Void> cancelReservation(@PathVariable long reservationId,
                                                @RequestBody(required = false) PatchReservationReq patchReservationReq)
            throws BaseException, IamportResponseException, IOException {

        reservationService.cancelReservation(jwtService.getMemberId(), reservationId, patchReservationReq);
        return new BaseResponse<>();
    }

}
