package com.oneline.shimpyo.repository.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ReservationQuerydsl {
    private final JPAQueryFactory jqf;

    public ReservationQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }
}
