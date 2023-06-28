package com.oneline.shimpyo.service.impl;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.BaseResponseStatus;
import com.oneline.shimpyo.domain.GetPageRes;
import com.oneline.shimpyo.domain.house.House;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.reservation.Reservation;
import com.oneline.shimpyo.domain.review.Review;
import com.oneline.shimpyo.domain.review.dto.GetReviewRes;
import com.oneline.shimpyo.domain.review.dto.PatchReviewReq;
import com.oneline.shimpyo.domain.review.dto.PostReviewReq;
import com.oneline.shimpyo.repository.ReservationRepository;
import com.oneline.shimpyo.repository.ReviewRepository;
import com.oneline.shimpyo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.oneline.shimpyo.domain.BaseResponseStatus.*;
import static com.oneline.shimpyo.domain.reservation.ReservationStatus.FINISHED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    @Override
    public void createReview(long memberId, PostReviewReq postReviewReq) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(MEMBER_NONEXISTENT));
        Reservation reservation = reservationRepository.findById(postReviewReq.getReservationId())
                .orElseThrow(() -> new BaseException(RESERVATION_NONEXISTENT));

        validateCreateReview(memberId, reservation);

        House house = reservation.getRoom().getHouse();

        Review review = Review.builder().member(member).house(house).reservation(reservation)
                .contents(postReviewReq.getContents()).reviewRating(postReviewReq.getReviewRating()).build();
        reviewRepository.save(review);
    }

    @Override
    public GetPageRes<GetReviewRes> readReviewList(long memberId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByMemberId(memberId, pageable);

        List<GetReviewRes> list = reviews.stream().map(GetReviewRes::new).collect(Collectors.toList());
        return new GetPageRes<>(reviews.getTotalPages(), reviews.getTotalElements(), reviews.getSize(),
                reviews.getNumberOfElements(), list);
    }

    @Transactional
    @Override
    public void updateReview(long reviewId, PatchReviewReq patchReviewReq) {
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

    private void validateCreateReview(long memberId, Reservation reservation) {
        //이미 있는 리뷰인지 확인
        Optional<Review> review = reviewRepository.findByReservationId(reservation.getId());
        if(review.isPresent()){
            throw new BaseException(REVIEW_ALREADY_EXIST);
        }

        if(reservation.getReservationStatus() != FINISHED){
            throw new BaseException(REVIEW_RESERVATION_WRONG_STATUS);
        }

        if(reservation.getMember().getId() != memberId){
            throw new BaseException(INVALID_MEMBER);
        }
    }

}
