package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
