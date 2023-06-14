package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.review.dto.GetReviewRes;
import com.oneline.shimpyo.domain.review.dto.PatchReviewReq;
import com.oneline.shimpyo.domain.review.dto.PostReviewReq;
import com.oneline.shimpyo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public BaseResponse<Void> createReview(@RequestBody PostReviewReq postReviewReq){

        reviewService.createReview(2L, postReviewReq);
        return new BaseResponse<>();
    }

    @GetMapping("/members/{memberId}")
    public BaseResponse<List<GetReviewRes>> readReviewList(@PathVariable long memberId){
        //todo check use paging
        return new BaseResponse<>(reviewService.getReviewList(2L));
    }

    @PatchMapping("/{reviewId}")
    public BaseResponse<Void> updateReview(@PathVariable long reviewId, @RequestBody PatchReviewReq patchReviewReq){
        // todo check authorization
        reviewService.updateReview(2L, reviewId, patchReviewReq);
        return new BaseResponse<>();
    }

    @DeleteMapping("/{reviewId}")
    public BaseResponse<Void> deleteReview(@PathVariable long reviewId){
        // todo check authorization
        reviewService.deleteReview(reviewId);
        return new BaseResponse<>();
    }

}
