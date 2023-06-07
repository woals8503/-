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

    private String name;
    private String description;
    private String expiredDate;

    @QueryProjection
    public CouponReq(String name, String description, LocalDateTime expiredDate) {
        this.name = name;
        this.description = description;
        this.expiredDate = expiredDate.toString();
    }
}
