package com.oneline.shimpyo.domain.reservation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
public class CouponReq {

    private long id;
    private String name;
    private String description;
    private int discount;
    private String expiredDate;

    @QueryProjection
    public CouponReq(long id, String name, String description, int discount, LocalDateTime expiredDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.expiredDate = expiredDate.toString(); //todo change
    }
}
