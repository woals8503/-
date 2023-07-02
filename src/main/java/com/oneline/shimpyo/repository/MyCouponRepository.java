package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.coupon.CouponId;
import com.oneline.shimpyo.domain.coupon.MyCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyCouponRepository extends JpaRepository<MyCoupon, Long> {

    Optional<MyCoupon> findByCouponId(CouponId couponId);
}
