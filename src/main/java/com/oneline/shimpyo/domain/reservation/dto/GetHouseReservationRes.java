package com.oneline.shimpyo.domain.reservation.dto;

import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class GetHouseReservationRes {

    private List<HostHouseReq> houseList;
    private List<HostReservationReq> reservationList;
    private long completeCount;
    private long usingCount;
    private long finishedCount;

    public GetHouseReservationRes(List<HostHouseReq> houseList, List<HostReservationReq> reservationList) {
        this.houseList = houseList;
        this.reservationList = reservationList;
    }

    public GetHouseReservationRes(List<HostHouseReq> houseList, List<HostReservationReq> reservationList,
                                  List<ReservationStatusCount> statusCountList) {
        this.houseList = houseList;
        this.reservationList = reservationList;

        for (ReservationStatusCount one : statusCountList) {
            if(one.getReservationStatus().equals(ReservationStatus.COMPLETE)){
                completeCount = one.getCount();
            }
            else if(one.getReservationStatus().equals(ReservationStatus.USING)){
                usingCount = one.getCount();
            }
            else if(one.getReservationStatus().equals(ReservationStatus.FINISHED)){
                finishedCount = one.getCount();
            }
        }
    }



}