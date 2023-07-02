package com.oneline.shimpyo.service.impl;

import com.oneline.shimpyo.domain.coupon.dto.GetCouponRes;
import com.oneline.shimpyo.repository.dsl.MyCouponQuerydsl;
import com.oneline.shimpyo.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponServiceImpl implements CouponService {

    private final MyCouponQuerydsl myCouponQuerydsl;

    @Override
    public List<GetCouponRes> readCouponList(long memberId) {
        return myCouponQuerydsl.getMyCouponList(memberId);
    }
}
