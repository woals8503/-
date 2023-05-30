package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.pay.PayMent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PayMent, Long> {

}
