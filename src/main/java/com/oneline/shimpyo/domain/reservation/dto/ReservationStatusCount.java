package com.oneline.shimpyo.domain.reservation.dto;

import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class ReservationStatusCount {
    private ReservationStatus reservationStatus;
    private long count;

    @QueryProjection
    public ReservationStatusCount(ReservationStatus reservationStatus, long count) {
        this.reservationStatus = reservationStatus;
        this.count = count;
    }
}
