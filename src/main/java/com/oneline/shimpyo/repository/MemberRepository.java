package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.email = :username")
    Member findByEmail(@Param("username") String username);
}
