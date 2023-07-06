package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.reservation.NonMemberReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NonMemberReservationRepository extends JpaRepository<NonMemberReservation, Long> {

    @Query("select nmr from NonMemberReservation nmr where nmr.payMent.UUID = :merchantUid")
    Optional<NonMemberReservation> findByMerchantUid(@Param("merchantUid") String merchantUid);
}
