package com.oneline.shimpyo.domain.reservation.dto;

import com.oneline.shimpyo.domain.house.HouseType;
import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private double lat;
    private double lng;
    private String address;
    private ReservationStatus reservationStatus;
    private boolean existReview;

    @QueryProjection
    public GetReservationListRes(long reservationId, String houseImageUrl, String houseName,
                                 String houseOwnerName, HouseType houseType, LocalDateTime checkInDate,
                                 LocalDateTime checkOutDate, LocalTime checkInTime, LocalTime checkoutTime,
                                 double lat, double lng, String address, ReservationStatus reservationStatus,
                                 long reviewId) {
        this.reservationId = reservationId;
        this.houseImageUrl = houseImageUrl;
        this.houseName = houseName;
        this.houseOwnerName = houseOwnerName;
        this.houseType = houseType;
        this.checkInDate = checkInDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "." +
                checkInTime.format(DateTimeFormatter.ofPattern("HH"));
        this.checkOutDate = checkOutDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "." +
                checkoutTime.format(DateTimeFormatter.ofPattern("HH"));
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.reservationStatus = reservationStatus;
        this.existReview = reviewId != 0L;
    }
}
