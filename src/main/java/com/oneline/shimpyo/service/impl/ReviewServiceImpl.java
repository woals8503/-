package com.oneline.shimpyo.service.impl;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.BaseResponseStatus;
import com.oneline.shimpyo.domain.house.House;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.reservation.Reservation;
import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import com.oneline.shimpyo.domain.review.Review;
import com.oneline.shimpyo.domain.review.dto.GetReviewRes;
import com.oneline.shimpyo.domain.review.dto.PatchReviewReq;
import com.oneline.shimpyo.domain.review.dto.PostReviewReq;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.repository.ReservationRepository;
import com.oneline.shimpyo.repository.ReviewRepository;
import com.oneline.shimpyo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.oneline.shimpyo.domain.BaseResponseStatus.*;
import static com.oneline.shimpyo.domain.reservation.ReservationStatus.FINISHED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private MemberRepository memberRepository;
    private ReservationRepository reservationRepository;

    @Transactional
    @Override
    public void createReview(long memberId, PostReviewReq postReviewReq) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(MEMBER_NONEXISTENT));
        Reservation reservation = reservationRepository.findById(postReviewReq.getReservationId())
                .orElseThrow(() -> new BaseException(RESERVATION_NONEXISTENT));

        if(reservation.getReservationStatus() != FINISHED){
            throw new BaseException(REVIEW_RESERVATION_WRONG_STATUS);
        }

        if(reservation.getMember().getId() != memberId){
            throw new BaseException(REVIEW_RESERVATION_WRONG_STATUS);
        }

        House house = reservation.getRoom().getHouse();

        Review review = Review.builder().member(member).house(house)
                .contents(postReviewReq.getContents()).reviewRating(postReviewReq.getReviewRating()).build();
        reviewRepository.save(review);
    }

    @Transactional
    @Override
    public void updateReview(long memberId, long reviewId, PatchReviewReq patchReviewReq) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REVIEW_NONEXISTENT));

        review.setContents(patchReviewReq.getContents());
        review.setReviewRating(patchReviewReq.getReviewRating());
    }

    @Transactional
    @Override
    public void deleteReview(long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REVIEW_NONEXISTENT));

        reviewRepository.delete(review);
    }

    @Override
    public List<GetReviewRes> getReviewList(long memberId) {
        List<Review> reviewList = reviewRepository.findByMemberId(memberId);

        return reviewList.stream().map(GetReviewRes::new).collect(Collectors.toList());
    }


}
