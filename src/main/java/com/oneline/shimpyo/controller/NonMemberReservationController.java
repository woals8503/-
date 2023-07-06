package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.reservation.dto.*;
import com.oneline.shimpyo.service.NonMemberReservationService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/non-member-reservations")
public class NonMemberReservationController {

    private final NonMemberReservationService nonMemberReservationService;

    @PostMapping("")
    public BaseResponse<PostReservationRes> createReservation(@RequestBody PostReservationReq postReservationReq)
            throws IamportResponseException, BaseException, IOException {

        long reservationId = nonMemberReservationService.createReservation(postReservationReq);
        return new BaseResponse<>(new PostReservationRes(reservationId));
    }

    @GetMapping("/{merchantUid}")
    public BaseResponse<GetReservationRes> readReservation(@PathVariable String merchantUid) {
        return new BaseResponse<>(nonMemberReservationService.readReservation(merchantUid));
    }

    @PatchMapping("/{merchantUid}")
    public BaseResponse<Void> updateReservationPeopleCount(@PathVariable String merchantUid,
                                                           @RequestBody PatchReservationPeopleReq patchReservationPeopleReq){

        nonMemberReservationService.updateReservationPeopleCount(merchantUid,
                patchReservationPeopleReq.getPeopleCount());
        return new BaseResponse<>();
    }

    @DeleteMapping("/{merchantUid}")
    public BaseResponse<Void> cancelReservation(@PathVariable String merchantUid,
                                                @RequestBody PatchReservationReq patchReservationReq)
            throws BaseException, IamportResponseException, IOException {
        nonMemberReservationService.cancelReservation(merchantUid, patchReservationReq);
        return new BaseResponse<>();
    }
}
