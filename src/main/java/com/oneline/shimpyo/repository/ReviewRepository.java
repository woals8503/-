package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.coupon.Coupon;
import com.oneline.shimpyo.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
