package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.GetPageRes;
import com.oneline.shimpyo.domain.review.dto.GetReviewRes;
import com.oneline.shimpyo.domain.review.dto.PatchReviewReq;
import com.oneline.shimpyo.domain.review.dto.PostReviewReq;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    void createReview(long memberId, PostReviewReq postReviewReq);

    void updateReview(long reviewId, PatchReviewReq patchReviewReq);

    void deleteReview(long reviewId);

    GetPageRes<GetReviewRes> readReviewList(long memberId, Pageable pageable);
}
