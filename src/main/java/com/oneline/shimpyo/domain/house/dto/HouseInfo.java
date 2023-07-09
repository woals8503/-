package com.oneline.shimpyo.domain.house.dto;

import com.oneline.shimpyo.domain.house.HouseType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class HouseInfo {
    private long houseId;
    private String name;
    private HouseType type;
    private List<String> options;
    private String contents;
    private String postCode;
    private String sido;
    private String sigungu;
    private String fullAddress;
    private double lat;
    private double lng;
    private List<String> houseImages;

    @QueryProjection
    public HouseInfo(long houseId, String name, HouseType type, String contents,
                     String postCode, String sido, String sigungu, String fullAddress, double lat, double lng) {
        this.houseId = houseId;
        this.name = name;
        this.type = type;
        this.contents = contents;
        this.postCode = postCode;
        this.sido = sido;
        this.sigungu = sigungu;
        this.fullAddress = fullAddress;
        this.lat = lat;
        this.lng = lng;
    }
}
