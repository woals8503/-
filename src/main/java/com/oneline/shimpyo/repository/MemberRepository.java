package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.email = :email")
    Member findByEmail(@Param("email") String email);

    @Query("select m from Member m where m.nickname = :nickname")
    Member findByNickname(@Param("nickname") String nickname);

    @Query("select m.phoneNumber from Member m where m.phoneNumber = :phoneNumber")
    String findByEmailWithPhonNumber(@Param("phoneNumber") String phoneNumber);

    @Query("select m from Member m where m.phoneNumber = :phoneNumber")
    Member findByMemberWithPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Modifying
    @Query("update Member m set m.refreshToken = null where m.id = :id")
    void removeRefreshToken(@Param("id") Long id);

    @Query("select m from Member m where m.refreshToken = :refreshToken")
    Optional<Member> findByRefreshToken(@Param("refreshToken") String refreshToken);
}
