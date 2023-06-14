package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.member.id = :member_id")
    List<Review> findByMemberId(@Param("member_id") long member_id);
}
