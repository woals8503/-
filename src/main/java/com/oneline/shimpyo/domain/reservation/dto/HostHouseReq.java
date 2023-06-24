package com.oneline.shimpyo.domain.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HostHouseReq {

    private long houseId;
    private String houseName;
    private String houseImageUrl;

    public HostHouseReq(long houseId, String houseName, String houseImageUrl) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.houseImageUrl = houseImageUrl;
    }
}
