package com.oneline.shimpyo.domain.reservation.dto;

import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class GetHouseReservationRes {

    private List<HostReservationReq> reservationList;
    private long completeCount;
    private long usingCount;
    private long finishedCount;
    private int totalPage;
    private long totalElements;
    private int pageSize;
    private int numberOfElements;

    public GetHouseReservationRes(Page<HostReservationReq> reservationList,
                                  List<ReservationStatusCount> statusCountList) {
        this.reservationList = reservationList.getContent();

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

        this.totalPage = reservationList.getTotalPages();
        this.totalElements = reservationList.getTotalElements();
        this.pageSize = reservationList.getSize();
        this.numberOfElements = reservationList.getNumberOfElements();
    }

}