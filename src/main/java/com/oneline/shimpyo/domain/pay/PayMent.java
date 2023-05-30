package com.oneline.shimpyo.domain.pay;

import com.oneline.shimpyo.domain.base.BaseEntity;
import com.oneline.shimpyo.domain.coupon.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "PAYMENT")
public class PayMent extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "pay_id")
    private Long id;

    @NotNull
    @Enumerated
    private PayStatus payStatus;

    @NotNull
    @Enumerated
    private PayMethod payMethod;

    @NotNull
    private int price;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

}
