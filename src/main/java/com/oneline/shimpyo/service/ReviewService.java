package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.review.dto.GetHouseReviewListRes;
import com.oneline.shimpyo.domain.review.dto.GetReviewRes;
import com.oneline.shimpyo.domain.review.dto.PatchReviewReq;
import com.oneline.shimpyo.domain.review.dto.PostReviewReq;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    long createReview(long memberId, PostReviewReq postReviewReq);

    long updateReview(long reviewId, PatchReviewReq patchReviewReq);

    long deleteReview(long reviewId);

    List<GetReviewRes> readReviewList(long memberId);

    GetHouseReviewListRes readHouseReviewList(long houseId, Pageable pageable);
}
