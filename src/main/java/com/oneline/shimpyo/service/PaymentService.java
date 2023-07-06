package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.pay.PayMent;
import com.oneline.shimpyo.domain.reservation.NonMemberReservation;
import com.oneline.shimpyo.domain.reservation.Reservation;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PatchReservationReq;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface PaymentService {

    PayMent createMemberPayment(long memberId, PostReservationReq postReservationReq)
            throws IamportResponseException, IOException, BaseException;
    Reservation cancelMemberPayment(long memberId, long reservationId, PatchReservationReq patchReservationReq)
            throws IamportResponseException, IOException, BaseException;

    PayMent createNonMemberPayment(PostReservationReq postReservationReq) throws IamportResponseException, IOException;

    NonMemberReservation cancelNonMemberPayment(NonMemberReservation nonMemberReservation, PatchReservationReq patchReservationReq)
            throws IamportResponseException, IOException;
}
