package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.pay.PayMent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PayMent, Long> {

    @Query("select p from PayMent p where p.UUID = :UUID or p.impUid = :impUid")
    Optional<PayMent> duplicateCheck(@Param("UUID") String UUID, @Param("impUid") String impUid);
}
