package com.oneline.shimpyo.domain.reservation.dto;

import com.oneline.shimpyo.domain.house.HouseType;
import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class GetReservationListRes {

    private long reservationId;
    private String houseImagePath;
    private String houseImageSavedFileName;
    private String houseName;
    private String houseOwnerName;
    private HouseType houseType;
    private String checkInDate;
    private String checkOutDate;
    private String address;
    private ReservationStatus reservationStatus;

    @QueryProjection
    public GetReservationListRes(long reservationId, String houseImagePath, String houseImageSavedFileName, String houseName,
                                 String houseOwnerName, HouseType houseType, LocalDateTime checkInDate,
                                 LocalDateTime checkOutDate, String address, ReservationStatus reservationStatus) {
        this.reservationId = reservationId;
        this.houseImagePath = houseImagePath;
        this.houseImageSavedFileName = houseImageSavedFileName;
        this.houseName = houseName;
        this.houseOwnerName = houseOwnerName;
        this.houseType = houseType;
        this.checkInDate = checkInDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.checkOutDate = checkOutDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.address = address;
        this.reservationStatus = reservationStatus;
    }
}
