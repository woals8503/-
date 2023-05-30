package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.coupon.Coupon;
import com.oneline.shimpyo.domain.wish.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {
}
