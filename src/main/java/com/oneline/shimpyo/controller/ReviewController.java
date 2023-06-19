package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.review.dto.GetReviewRes;
import com.oneline.shimpyo.domain.review.dto.PatchReviewReq;
import com.oneline.shimpyo.domain.review.dto.PostReviewReq;
import com.oneline.shimpyo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public BaseResponse<Void> createReview(@RequestBody PostReviewReq postReviewReq){
        reviewService.createReview(8L, postReviewReq);
        return new BaseResponse<>();
    }

    @GetMapping("/members/{memberId}")
    public BaseResponse<List<GetReviewRes>> readReviewList(@PathVariable long memberId,
                                                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                           Pageable pageable){
        // todo check authorization
        return new BaseResponse<>(reviewService.readReviewList(memberId, pageable));
    }

    @PatchMapping("/{reviewId}")
    public BaseResponse<Void> updateReview(@PathVariable long reviewId, @RequestBody PatchReviewReq patchReviewReq){
        // todo check authorization
        reviewService.updateReview(8L, reviewId, patchReviewReq);
        return new BaseResponse<>();
    }

    @DeleteMapping("/{reviewId}")
    public BaseResponse<Void> deleteReview(@PathVariable long reviewId){
        // todo check authorization
        reviewService.deleteReview(reviewId);
        return new BaseResponse<>();
    }

}
