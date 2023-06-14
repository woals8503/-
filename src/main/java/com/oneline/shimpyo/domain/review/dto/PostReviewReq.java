package com.oneline.shimpyo.domain.review.dto;

import com.oneline.shimpyo.domain.review.ReviewRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostReviewReq {

    private long reservationId;
    private String contents;
    private ReviewRating reviewRating;
}
