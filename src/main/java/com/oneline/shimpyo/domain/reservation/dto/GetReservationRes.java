package com.oneline.shimpyo.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetReservationRes {

    @JsonIgnore
    private long reservationId;
    private long houseId;
    private ReservationStatus reservationStatus;
    private List<String> houseImageUrl;
    private String houseName;
    private String houseOwnerName;
    private String checkInDate;
    private String checkOutDate;
    private long roomId;
    private String roomName;
    private int peopleCount;
    private String address;
    private int price;
    private int remainPrice;

    @QueryProjection
    public GetReservationRes(long houseId, long reservationId, ReservationStatus reservationStatus,
                             String houseName, String houseOwnerName, LocalDateTime checkInDate,
                             LocalDateTime checkOutDate, LocalTime checkInTime, LocalTime checkoutTime,
                             long roomId, String roomName, int peopleCount, String address, int price, int remainPrice) {
        this.houseId = houseId;
        this.reservationId = reservationId;
        this.reservationStatus = reservationStatus;
        this.houseName = houseName;
        this.houseOwnerName = houseOwnerName;
        this.checkInDate = checkInDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "." +
                checkInTime.format(DateTimeFormatter.ofPattern("HH"));
        this.checkOutDate = checkOutDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "." +
                checkoutTime.format(DateTimeFormatter.ofPattern("HH"));
        this.roomId = roomId;
        this.roomName = roomName;
        this.peopleCount = peopleCount;
        this.address = address;
        this.price = price;
        this.remainPrice = remainPrice;
    }

    public void setHouseImageUrl(List<String> houseImageUrl) {
        this.houseImageUrl = houseImageUrl;
    }
}