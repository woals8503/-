package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.coupon.MyCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCouponRepository extends JpaRepository<MyCoupon, Long> {

}
