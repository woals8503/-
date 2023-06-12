package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PutReservationReq;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface ReservationService {

    GetPrepareReservationReq prepareReservation(long memberId) throws BaseException;
    long createReservation(long memberId, PostReservationReq postReservationReq)
            throws BaseException, IamportResponseException, IOException;
    void cancelReservation(long reservationId, PutReservationReq putReservationReq)
            throws BaseException, IamportResponseException, IOException;
}
