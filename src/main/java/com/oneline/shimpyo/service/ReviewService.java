package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.review.dto.GetReviewRes;
import com.oneline.shimpyo.domain.review.dto.PatchReviewReq;
import com.oneline.shimpyo.domain.review.dto.PostReviewReq;

import java.util.List;

public interface ReviewService {
    void createReview(long memberId, PostReviewReq postReviewReq);

    void updateReview(long reviewId, PatchReviewReq patchReviewReq);

    void deleteReview(long reviewId);

    List<GetReviewRes> readReviewList(long memberId);
}
