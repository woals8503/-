package com.oneline.shimpyo.repository.dsl;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.QMember;
import com.oneline.shimpyo.domain.member.dto.ResetPasswordReq;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.oneline.shimpyo.domain.member.QMember.*;

@Repository
public class MemberQuerydsl {
    private final JPAQueryFactory jqf;

    public MemberQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }


    public List<Member> findByEmailListWithPhoneNumber(String phoneNumber) {
        return jqf.select(member)
                .from(member)
                .where(member.phoneNumber.eq(phoneNumber))
                .fetch();
    }

    public Member findByMemberWithPhoneNumber(String phoneNumber) {
        return jqf.select(member)
                .from(member)
                .where(member.social.eq(false)
                        .and(member.phoneNumber.eq(phoneNumber)))
                .fetchOne();
    }
}
