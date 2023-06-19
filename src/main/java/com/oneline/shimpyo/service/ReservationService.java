package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.GetPageRes;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationRes;
import com.oneline.shimpyo.domain.reservation.dto.GetReservationListRes;
import com.oneline.shimpyo.domain.reservation.dto.PatchReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.siot.IamportRestClient.exception.IamportResponseException;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ReservationService {

    GetPrepareReservationRes prepareReservation(long memberId) throws BaseException;
    long createReservation(long memberId, PostReservationReq postReservationReq)
            throws BaseException, IamportResponseException, IOException;
    GetPageRes<GetReservationListRes> readReservationList(long memberId, Pageable pageable);
    void cancelReservation(long memberId, long reservationId, PatchReservationReq patchReservationReq)
            throws BaseException, IamportResponseException, IOException;

}
