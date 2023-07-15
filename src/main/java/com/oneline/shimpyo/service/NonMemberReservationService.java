package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.reservation.dto.GetReservationRes;
import com.oneline.shimpyo.domain.reservation.dto.PatchReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;

public interface NonMemberReservationService {

    String createReservation(PostReservationReq postReservationReq) throws IamportResponseException, IOException;

    GetReservationRes readReservation(String merchantUid);

    void updateReservationPeopleCount(String merchantUid, int peopleCount);

    void cancelReservation(String merchantUid, PatchReservationReq patchReservationReq) throws IamportResponseException, IOException;
}
