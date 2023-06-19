package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.GetPageRes;
import com.oneline.shimpyo.domain.review.dto.GetReviewRes;
import com.oneline.shimpyo.domain.review.dto.PatchReviewReq;
import com.oneline.shimpyo.domain.review.dto.PostReviewReq;
import com.oneline.shimpyo.security.jwt.JwtService;
import com.oneline.shimpyo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final JwtService jwtService;
    private final ReviewService reviewService;

    @PostMapping("")
    public BaseResponse<Void> createReview(@RequestBody PostReviewReq postReviewReq){
        reviewService.createReview(jwtService.getMemberId(), postReviewReq);
        return new BaseResponse<>();
    }

    @GetMapping("/members/{memberId}")
    public BaseResponse<GetPageRes<GetReviewRes>> readReviewList(
            @PathVariable long memberId,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        jwtService.checkJwtByStoreId(memberId);
        return new BaseResponse<>(reviewService.readReviewList(memberId, pageable));
    }

    @PatchMapping("/{reviewId}")
    public BaseResponse<Void> updateReview(@PathVariable long reviewId, @RequestBody PatchReviewReq patchReviewReq){

        reviewService.updateReview(jwtService.getMemberId(), reviewId, patchReviewReq);
        return new BaseResponse<>();
    }

    @DeleteMapping("/{reviewId}")
    public BaseResponse<Void> deleteReview(@PathVariable long reviewId){

        reviewService.deleteReview(jwtService.getMemberId(), reviewId);
        return new BaseResponse<>();
    }

}
