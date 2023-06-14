package com.oneline.shimpyo.domain.reservation.dto;

import com.oneline.shimpyo.domain.pay.PayMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostReservationReq {

    private String impUid;
    private long roomId;
    private long couponId;
    private String merchantUid;
    private PayMethod payMethod;
    private int peopleCount;
    private String phoneNumber;
    private String checkInDate;
    private String checkOutDate;

    public LocalDateTime stringToLocalDateTime(String date){
        String[] split = date.split("\\.");
        int y = Integer.parseInt(split[0]);
        int m = Integer.parseInt(split[1]);
        int d = Integer.parseInt(split[2]);
        return LocalDateTime.of(y, m, d, 0, 0, 0);
    }
}
