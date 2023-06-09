package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationReq;

public interface ReservationService {

    GetPrepareReservationReq prepareReservation(long memberId) throws BaseException;
    long createReservation(long memberId, long paymentId, PostReservationReq postReservationReq) throws BaseException;
}
