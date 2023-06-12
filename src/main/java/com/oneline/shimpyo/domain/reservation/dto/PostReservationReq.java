package com.oneline.shimpyo.domain.reservation.dto;

import com.oneline.shimpyo.domain.house.HouseType;
import com.oneline.shimpyo.domain.pay.PayMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
public class PostReservationReq {

    private String impUid;
    private HouseType houseType;
    private long houseRoomId;
    private long couponId;
    private String merchantUid;
    private PayMethod payMethod;
    private int peopleCount;
    private String phoneNumber;
    private String checkInDate;
    private String checkOutDate;

    public PostReservationReq(String impUid, HouseType houseType, long houseRoomId, long couponId, String merchantUid,
                              PayMethod payMethod, int peopleCount, String phoneNumber, String checkInDate, String checkOutDate) {
        this.impUid = impUid;
        this.houseType = houseType;
        this.houseRoomId = houseRoomId;
        this.couponId = couponId;
        this.merchantUid = merchantUid;
        this.payMethod = payMethod;
        this.peopleCount = peopleCount;
        this.phoneNumber = phoneNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public LocalDateTime stringToLocalDateTime(String date){
        String[] split = date.split("\\.");
        int y = Integer.parseInt(split[0]);
        int m = Integer.parseInt(split[1]);
        int d = Integer.parseInt(split[2]);
        return LocalDateTime.of(y, m, d, 0, 0, 0);
    }
}
