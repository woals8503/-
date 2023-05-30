package com.oneline.shimpyo.repository.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ReviewQuerydsl {
    private final JPAQueryFactory jqf;

    public ReviewQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }
}
