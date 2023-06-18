package com.oneline.shimpyo.domain.house.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AddressReq {
    private String postCode;
    private String sido;
    private String sigungu;
    private String fullAddress;
    private double lat;
    private double lng;
}
