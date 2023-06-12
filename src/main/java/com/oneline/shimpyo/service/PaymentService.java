package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.pay.PayMent;
import com.oneline.shimpyo.domain.reservation.Reservation;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PutReservationReq;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface PaymentService {

    PayMent createPayment(long memberId, PostReservationReq postReservationReq)
            throws IamportResponseException, IOException, BaseException;
    Reservation cancelPayment(long reservationId, PutReservationReq putReservationReq)
            throws IamportResponseException, IOException, BaseException;

}
