package com.oneline.shimpyo.domain.member;

import com.oneline.shimpyo.domain.base.BaseEntity;
import com.oneline.shimpyo.domain.coupon.MyCoupon;
import com.oneline.shimpyo.domain.house.House;
import com.oneline.shimpyo.domain.reservation.Reservation;
import com.oneline.shimpyo.domain.review.Review;
import com.oneline.shimpyo.domain.wish.Wish;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Builder
@AllArgsConstructor
@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "MEMBER")
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String phoneNumber;
    @NotNull
    private int point;
    @NotNull
    private String nickname;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_grade_id")
    private MemberGrade memberGrade;

    @Enumerated(value = STRING)
    private MemberRole role;

    @OneToOne(mappedBy = "member", cascade = ALL)
    private MemberImage memberImage;

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<MyCoupon> myCoupons = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Wish> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<House> houseList = new ArrayList<>();

}
