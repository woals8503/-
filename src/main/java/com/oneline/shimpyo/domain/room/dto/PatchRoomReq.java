package com.oneline.shimpyo.domain.room.dto;

import com.oneline.shimpyo.domain.house.dto.PatchImageReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatchRoomReq {
    private long id;
    private String name;
    private int price;
    private int minPeople;
    private int maxPeople;
    private int bedCount;
    private int bedroomCount;
    private int bathroomCount;
    private int totalCount;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private List<PatchImageReq> patchImageReqs;
}
