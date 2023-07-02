package com.oneline.shimpyo.repository.dsl;

import com.oneline.shimpyo.domain.coupon.dto.GetCouponRes;
import com.oneline.shimpyo.domain.coupon.dto.QGetCouponRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.oneline.shimpyo.domain.coupon.QCoupon.coupon;
import static com.oneline.shimpyo.domain.coupon.QMyCoupon.myCoupon;

@Repository
public class MyCouponQuerydsl {

    private final JPAQueryFactory jqf;

    public MyCouponQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }

    public List<GetCouponRes> getMyCouponList(long memberId){
        return jqf.select(new QGetCouponRes(coupon.id, coupon.name, coupon.description,
                        coupon.discount, myCoupon.expiredDate))
                .from(myCoupon)
                .join(myCoupon.coupon, coupon)
                .on(myCoupon.coupon.id.eq(coupon.id))
                .where(myCoupon.member.id.eq(memberId))
                .where(coupon.isActived.isTrue().and(myCoupon.isUsed.isFalse()))
                .fetch();
    }
}
