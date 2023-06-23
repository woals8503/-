package com.oneline.shimpyo.domain.house.dto;

import com.oneline.shimpyo.domain.house.HouseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatchHouseReq {
    private long id;
    private String name;
    private HouseType type;
    private OptionReq option;
    private AddressReq address;
    private String contents;
    private List<PatchImageReq> patchImageReqs;


}
