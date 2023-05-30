package com.oneline.shimpyo.repository.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PaymentQuerydsl {
    private final JPAQueryFactory jqf;

    public PaymentQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }
}
