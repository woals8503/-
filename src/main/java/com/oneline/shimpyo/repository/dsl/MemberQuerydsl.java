package com.oneline.shimpyo.repository.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class MemberQuerydsl {
    private final JPAQueryFactory jqf;

    public MemberQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }
}
