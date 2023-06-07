package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationReq;

public interface ReservationService {

    GetPrepareReservationReq prepareReservation(long memberId) throws BaseException;
}
