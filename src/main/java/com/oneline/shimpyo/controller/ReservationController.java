package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationRes;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationRes;
import com.oneline.shimpyo.domain.reservation.dto.PatchReservationReq;
import com.oneline.shimpyo.security.auth.PrincipalDetails;
import com.oneline.shimpyo.service.ReservationService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("")
    public BaseResponse<GetPrepareReservationRes> prepareReservation() throws BaseException {
        return new BaseResponse<>(reservationService.prepareReservation(8L));
    }

    @PostMapping("")
    public BaseResponse<PostReservationRes> createReservation(@RequestBody PostReservationReq postReservationReq)
            throws IamportResponseException, BaseException, IOException {

        long reservationId = reservationService.createReservation(8L, postReservationReq);
        return new BaseResponse<>(new PostReservationRes(reservationId));
    }

    @PatchMapping("/{reservationId}")
    public BaseResponse<Void> cancelReservation(@PathVariable long reservationId,
                                                @RequestBody PatchReservationReq patchReservationReq)
            throws BaseException, IamportResponseException, IOException {

        reservationService.cancelReservation(8L, reservationId, patchReservationReq);
        return new BaseResponse<>();
    }

}
