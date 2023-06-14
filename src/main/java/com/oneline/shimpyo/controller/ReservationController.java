package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationRes;
import com.oneline.shimpyo.domain.reservation.dto.PatchReservationReq;
import com.oneline.shimpyo.service.ReservationService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("")
    public BaseResponse<GetPrepareReservationReq> prepareReservation() throws BaseException {
        //todo JWT token
        return new BaseResponse<>(reservationService.prepareReservation(2L));
    }

    @PostMapping("")
    public BaseResponse<PostReservationRes> createReservation(@RequestBody PostReservationReq postReservationReq)
            throws IamportResponseException, BaseException, IOException {
        //todo JWT token
        long reservationId = reservationService.createReservation(2L, postReservationReq);
        return new BaseResponse<>(new PostReservationRes(reservationId));
    }

    @PatchMapping("/{reservationId}")
    public BaseResponse<Void> cancelReservation(@PathVariable long reservationId,
                                                @RequestBody PatchReservationReq patchReservationReq)
            throws BaseException, IamportResponseException, IOException {
        //todo check memberId
        reservationService.cancelReservation(reservationId, patchReservationReq);

        return new BaseResponse<>();
    }

}
