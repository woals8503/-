package com.oneline.shimpyo.security.oAuth.profile;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.MemberGrade;
import com.oneline.shimpyo.security.CustomBCryptPasswordEncoder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;

import static com.oneline.shimpyo.domain.member.GradeName.SILVER;
import static com.oneline.shimpyo.domain.member.MemberRole.CLIENT;

@Slf4j
@Getter
@Setter
public class MemberProfile {
    private String name;
    private String email;
    private String provider;

    public Member toMember(EntityManager em, CustomBCryptPasswordEncoder CustomBCryptPasswordEncoder) {
        MemberGrade memberGrade = new MemberGrade(SILVER, 3);
        em.persist(memberGrade);
        em.flush();

        return Member.builder()
                .email(email)   // -> email
                .password(CustomBCryptPasswordEncoder.encode("shimpyo"))
                .provider(provider) // -> google
                .memberGrade(memberGrade)
                .nickname("임시 닉네임")
                .social(false)
                .role(CLIENT)
                .point(0)
                .build();
    }

}