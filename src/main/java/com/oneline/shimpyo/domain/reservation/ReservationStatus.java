package com.oneline.shimpyo.domain.reservation;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    CANCEL("예약 취소"), COMPLETE("예약 완료"), WAITING("결제 대기중"), FINISHED("이용 완료");

    private String status;

    ReservationStatus(String status) {
        this.status = status;
    }
}
