package com.oneline.shimpyo.repository.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CouponQuerydsl {

    private final JPAQueryFactory jqf;

    public CouponQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }
}
