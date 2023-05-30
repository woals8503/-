package com.oneline.shimpyo.domain.coupon;

public enum MyCouponStatus {
    USED("이미 사용한 쿠폰"), AVAILABLE("사용 가능 쿠폰");
    private String status;

    MyCouponStatus(String status) {
        this.status = status;
    }
}
