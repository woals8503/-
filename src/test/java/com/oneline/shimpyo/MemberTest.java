package com.oneline.shimpyo;

import com.oneline.shimpyo.domain.member.MemberGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional(readOnly = false)
public class MemberTest {

    @Autowired
    EntityManager em;
    @Autowired
    TestRepositoryCustom testRepository;
    @Autowired
    TestService testService;

    @Test
    public void memberSaveTest() {
        MemberGrade grade = new MemberGrade(1L, "grade", 10);
        em.persist(grade);
        testService.addGrade(grade);

    }
}
