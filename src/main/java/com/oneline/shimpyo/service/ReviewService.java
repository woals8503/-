package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.review.dto.GetReviewRes;
import com.oneline.shimpyo.domain.review.dto.PatchReviewReq;
import com.oneline.shimpyo.domain.review.dto.PostReviewReq;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    void createReview(long memberId, PostReviewReq postReviewReq);

    void updateReview(long memberId, long reviewId, PatchReviewReq patchReviewReq);

    void deleteReview(long memberId, long reviewId);

    List<GetReviewRes> readReviewList(long memberId, Pageable pageable);
}
