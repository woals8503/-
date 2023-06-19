package com.oneline.shimpyo.domain.review.dto;

import com.oneline.shimpyo.domain.review.Review;
import com.oneline.shimpyo.domain.review.ReviewRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewRes {
    private long reviewId;
    private String reviewCreatedDate;
    private String reviewModifiedDate;
    private ReviewRating reviewRating;
    private String contents;

    public GetReviewRes(Review review){
        this.reviewId = review.getId();
        this.reviewCreatedDate = review.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));;
        this.reviewModifiedDate = review.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));;
        this.reviewRating = review.getReviewRating();
        this.contents = review.getContents();
    }

}
