package com.oneline.shimpyo.domain.coupon;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CouponId implements Serializable {

    @Column(name = "member_id")
    private Long member_id;

    @Column(name = "coupon_id")
    private Long coupon_id;

}
