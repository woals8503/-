package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface PaymentService {

    long createPayment(long memberId, PostReservationReq postReservationReq) throws IamportResponseException, IOException, BaseException;
}
