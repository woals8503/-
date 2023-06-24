package com.oneline.shimpyo.domain.house.dto;

import com.oneline.shimpyo.domain.house.HouseType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@Builder
@ToString
public class PostHouseReq {
    private String name;
    private HouseType type;
    private OptionReq option;
    private List<RoomReq> rooms;
    private AddressReq address;
    private String contents;
    private List<FileReq> files;

}
