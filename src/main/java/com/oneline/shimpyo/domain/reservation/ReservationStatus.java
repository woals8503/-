package com.oneline.shimpyo.domain.reservation;

import com.oneline.shimpyo.domain.BaseException;
import lombok.Getter;

import static com.oneline.shimpyo.domain.BaseResponseStatus.RESERVATION_STATUS_WRONG;

@Getter
public enum ReservationStatus {
    CANCEL("예약 취소"), COMPLETE("예약 완료"), USING("이용 중"), FINISHED("이용 완료");

    private String status;

    ReservationStatus(String status) {
        this.status = status;
    }

    public static ReservationStatus of(String str){
        if(str.isBlank()){
            return USING;
        }

        for (ReservationStatus value : ReservationStatus.values()) {
            if(value.name().equals(str)){
                return value;
            }
        }

        throw new BaseException(RESERVATION_STATUS_WRONG);
    }
}
