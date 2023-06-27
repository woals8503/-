package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.GetPageRes;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.review.dto.GetReviewRes;
import com.oneline.shimpyo.domain.review.dto.PatchReviewReq;
import com.oneline.shimpyo.domain.review.dto.PostReviewReq;
import com.oneline.shimpyo.modules.CheckMember;
import com.oneline.shimpyo.security.auth.CurrentMember;
import com.oneline.shimpyo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final CheckMember checkMember;

    @PostMapping("")
    public BaseResponse<Void> createReview(@RequestBody PostReviewReq postReviewReq,
                                           @CurrentMember Member member){
        long memberId = checkMember.getMemberId(member, true);
        reviewService.createReview(memberId, postReviewReq);
        return new BaseResponse<>();
    }

    @GetMapping("")
    public BaseResponse<GetPageRes<GetReviewRes>> readReviewList(
            @CurrentMember Member member,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        long memberId = checkMember.getMemberId(member, true);
        checkMember.checkCurrentMember(member, memberId);

        return new BaseResponse<>(reviewService.readReviewList(memberId, pageable));
    }

    @PatchMapping("/{reviewId}")
    public BaseResponse<Void> updateReview(@CurrentMember Member member, @PathVariable long reviewId,
                                           @RequestBody PatchReviewReq patchReviewReq){
        long memberId = checkMember.getMemberId(member, true);
        checkMember.checkCurrentMember(member, memberId);

        reviewService.updateReview(reviewId, patchReviewReq);
        return new BaseResponse<>();
    }

    @DeleteMapping("/{reviewId}")
    public BaseResponse<Void> deleteReview(@CurrentMember Member member, @PathVariable long reviewId){
        long memberId = checkMember.getMemberId(member, true);
        checkMember.checkCurrentMember(member, memberId);

        reviewService.deleteReview(reviewId);
        return new BaseResponse<>();
    }

}
