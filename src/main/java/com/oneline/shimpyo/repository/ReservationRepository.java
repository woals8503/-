package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
