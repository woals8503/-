package com.oneline.shimpyo.domain.reservation.dto;

import com.oneline.shimpyo.domain.pay.PayMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class PostReservationReq {

    private String impUid;
    private long roomId;
    private long couponId;
    private String merchantUid;
    private PayMethod payMethod;
    private int peopleCount;
    private String phoneNumber;
    private String checkInDate;
    private String checkOutDate;

    public PostReservationReq(String impUid, long roomId, long couponId, String merchantUid, PayMethod payMethod,
                              int peopleCount, String phoneNumber, String checkInDate, String checkOutDate) {
        this.impUid = impUid;
        this.roomId = roomId;
        this.couponId = couponId;
        this.merchantUid = merchantUid;
        this.payMethod = payMethod;
        this.peopleCount = peopleCount;
        this.phoneNumber = phoneNumber;
        //todo 추후 변경
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

}
