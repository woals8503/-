package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.coupon.dto.GetCouponRes;

import java.util.List;

public interface CouponService {
    List<GetCouponRes> readCouponList(long memberId);
}
