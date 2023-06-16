package com.oneline.shimpyo.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetPrepareReservationRes {

    private String merchantUid;
    private String grade;
    private int discount;
    private List<CouponReq> couponList;
}
