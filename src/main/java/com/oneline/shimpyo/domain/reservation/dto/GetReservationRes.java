package com.oneline.shimpyo.domain.reservation.dto;

import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetReservationRes {

    private long reservationId;
    private long houseId;
    private ReservationStatus reservationStatus;
    private List<String> houseImageUrl;
    private long houseOwnerId;
    private String houseName;
    private String houseOwnerName;
    private String checkInDate;
    private String checkOutDate;
    private long roomId;
    private String roomName;
    private int maxPeople;
    private int minPeople;
    private int peopleCount;
    private double lat;
    private double lng;
    private String address;
    private int price;
    private int remainPrice;
    private boolean existReview;

    @QueryProjection
    public GetReservationRes(long houseId, long reservationId, ReservationStatus reservationStatus, long houseOwnerId,
                             String houseName, String houseOwnerName, LocalDateTime checkInDate,
                             LocalDateTime checkOutDate, LocalTime checkInTime, LocalTime checkoutTime,
                             long roomId, String roomName, int maxPeople, int minPeople, int peopleCount, double lat,
                             double lng, String address, int price, int remainPrice, long reviewId) {
        this.houseId = houseId;
        this.reservationId = reservationId;
        this.reservationStatus = reservationStatus;
        this.houseOwnerId = houseOwnerId;
        this.houseName = houseName;
        this.houseOwnerName = houseOwnerName;
        this.checkInDate = checkInDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "." +
                checkInTime.format(DateTimeFormatter.ofPattern("HH"));
        this.checkOutDate = checkOutDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "." +
                checkoutTime.format(DateTimeFormatter.ofPattern("HH"));
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxPeople = maxPeople;
        this.minPeople = minPeople;
        this.peopleCount = peopleCount;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.price = price;
        this.remainPrice = remainPrice;
        this.existReview = reviewId != 0L;
    }

    @QueryProjection
    public GetReservationRes(long houseId, long reservationId, ReservationStatus reservationStatus, long houseOwnerId,
                             String houseName, String houseOwnerName, LocalDateTime checkInDate,
                             LocalDateTime checkOutDate, LocalTime checkInTime, LocalTime checkoutTime,
                             long roomId, String roomName, int maxPeople, int minPeople, int peopleCount, double lat,
                             double lng, String address, int price, int remainPrice) {
        this.houseId = houseId;
        this.reservationId = reservationId;
        this.reservationStatus = reservationStatus;
        this.houseOwnerId = houseOwnerId;
        this.houseName = houseName;
        this.houseOwnerName = houseOwnerName;
        this.checkInDate = checkInDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "." +
                checkInTime.format(DateTimeFormatter.ofPattern("HH"));
        this.checkOutDate = checkOutDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "." +
                checkoutTime.format(DateTimeFormatter.ofPattern("HH"));
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxPeople = maxPeople;
        this.minPeople = minPeople;
        this.peopleCount = peopleCount;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.price = price;
        this.remainPrice = remainPrice;
    }

    public void setHouseImageUrl(List<String> houseImageUrl) {
        this.houseImageUrl = houseImageUrl;
    }
}