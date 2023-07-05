package com.oneline.shimpyo.repository.dsl;

import com.oneline.shimpyo.domain.review.dto.GetReviewRes;
import com.oneline.shimpyo.domain.review.dto.QGetReviewRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oneline.shimpyo.domain.reservation.QReservation.reservation;
import static com.oneline.shimpyo.domain.review.QReview.review;

@Repository
public class ReviewQuerydsl {
    private final JPAQueryFactory jqf;

    public ReviewQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }

    public List<GetReviewRes> readReviewList(long memberId) {
        return jqf.select(new QGetReviewRes(reservation.id, review.id, reservation.createdDate,
                        review.lastModifiedDate, review.reviewRating, review.contents))
                .from(review)
                .join(review.reservation, reservation)
                .where(review.member.id.eq(memberId))
                .fetch();

    }
}
