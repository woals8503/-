package com.oneline.shimpyo.domain.reservation.dto;

import com.oneline.shimpyo.domain.house.HouseType;
import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@NoArgsConstructor
public class GetReservationListRes {

    private long reservationId;
    private String houseImageUrl;
    private String houseName;
    private String houseOwnerName;
    private HouseType houseType;
    private String checkInDate;
    private String checkOutDate;
    private String address;
    private ReservationStatus reservationStatus;

    @QueryProjection
    public GetReservationListRes(long reservationId, String houseImageUrl, String houseName,
                                 String houseOwnerName, HouseType houseType, LocalDateTime checkInDate,
                                 LocalDateTime checkOutDate, String address, ReservationStatus reservationStatus) {
        this.reservationId = reservationId;
        this.houseImageUrl = houseImageUrl;
        this.houseName = houseName;
        this.houseOwnerName = houseOwnerName;
        this.houseType = houseType;
        this.checkInDate = checkInDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.checkOutDate = checkOutDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.address = address;
        this.reservationStatus = reservationStatus;
    }
}
