package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationRes;
import com.oneline.shimpyo.domain.reservation.dto.PatchReservationReq;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface ReservationService {

    GetPrepareReservationRes prepareReservation(long memberId) throws BaseException;
    long createReservation(long memberId, PostReservationReq postReservationReq)
            throws BaseException, IamportResponseException, IOException;
    void cancelReservation(long memberId, long reservationId, PatchReservationReq patchReservationReq)
            throws BaseException, IamportResponseException, IOException;
}
