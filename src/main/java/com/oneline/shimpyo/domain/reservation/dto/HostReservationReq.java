package com.oneline.shimpyo.domain.reservation.dto;

import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class HostReservationReq {

    private long reservationId;
    private ReservationStatus reservationStatus;
    private String roomImageUrl;
    private String roomName;
    private String checkInDate;
    private String checkOutDate;
    private String personName;
    private int peopleCount;
    private String phoneNumber;

    @QueryProjection
    public HostReservationReq(long reservationId, ReservationStatus reservationStatus, String roomImageUrl, String roomName, LocalDateTime checkInDate,
                              LocalDateTime checkOutDate, LocalTime checkInTime, LocalTime checkoutTime,
                              String personName, int peopleCount, String phoneNumber) {
        this.reservationId = reservationId;
        this.reservationStatus = reservationStatus;
        this.roomImageUrl = roomImageUrl;
        this.roomName = roomName;
        this.checkInDate = checkInDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "." +
                checkInTime.format(DateTimeFormatter.ofPattern("HH"));
        this.checkOutDate = checkOutDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "." +
                checkoutTime.format(DateTimeFormatter.ofPattern("HH"));
        this.personName = personName;
        this.peopleCount = peopleCount;
        this.phoneNumber = phoneNumber;
    }
}
