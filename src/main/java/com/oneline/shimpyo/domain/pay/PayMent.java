package com.oneline.shimpyo.domain.pay;

import com.oneline.shimpyo.domain.base.BaseEntity;
import com.oneline.shimpyo.domain.coupon.Coupon;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "PAYMENT")
public class PayMent extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "pay_id")
    private Long id;

    @NotNull
    private String UUID;

    @NotNull
    private String impUid;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @NotNull
    private int price;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

}
