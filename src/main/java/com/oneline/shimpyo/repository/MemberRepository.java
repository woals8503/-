package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.dto.ResetPasswordReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.email = :email")
    Member findByEmail(@Param("email") String email);

    @Query("select m from Member m where m.nickname = :nickname")
    Member findByNickname(@Param("nickname") String nickname);

    @Query("select m.phoneNumber from Member m where m.phoneNumber = :phoneNumber")
    String findByEmailWithPhonNumber(@Param("phoneNumber") String phoneNumber);

    @Query("select m from Member m where m.phoneNumber = :phoneNumber")
    Member findByMemberWithPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
