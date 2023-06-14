package com.oneline.shimpyo.domain.review.dto;

import com.oneline.shimpyo.domain.review.ReviewRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatchReviewReq {

    private String contents;
    private ReviewRating reviewRating;
}
