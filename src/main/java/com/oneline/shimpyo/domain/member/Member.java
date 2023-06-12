package com.oneline.shimpyo.domain.member;

import com.oneline.shimpyo.domain.base.BaseEntity;
import com.oneline.shimpyo.domain.coupon.MyCoupon;
import com.oneline.shimpyo.domain.house.House;
import com.oneline.shimpyo.domain.member.dto.MemberReq;
import com.oneline.shimpyo.domain.reservation.Reservation;
import com.oneline.shimpyo.domain.review.Review;
import com.oneline.shimpyo.domain.wish.Wish;
import com.oneline.shimpyo.security.CustomBCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.oneline.shimpyo.domain.member.MemberRole.*;
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
    private int point;

    @NotNull
    private String nickname;

    private String phoneNumber;

    private String provider;
    private String providerId;

    private String refreshToken;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_grade_id")
    private MemberGrade memberGrade;

    @Enumerated(value = STRING)
    private MemberRole role;

    @OneToOne(mappedBy = "member", cascade = ALL, fetch = LAZY)
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

    @Builder
    public Member(String email, String password, String phoneNumber, int point, String nickname, String provider, String providerId, MemberGrade memberGrade, MemberRole role) {
        this.email = email;
        this.password = password;
        this.point = point;
        this.nickname = nickname;
        this.provider = provider;
        this.providerId = providerId;
        this.memberGrade = memberGrade;
        this.role = role;
    }

    public Member(MemberReq request, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.email = request.getEmail();
        this.password = bCryptPasswordEncoder.encode(request.getPassword());
        this.phoneNumber = "010-1111-1111";
        this.nickname = request.getNickname();
        this.point = 0;
        this.role = CLIENT;
    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void resetPassword(String password, CustomBCryptPasswordEncoder bCryptPasswordEncoder) {
        this.password = bCryptPasswordEncoder.encode(password);
    }

    public void updateRefreshToken(String newToken) {
        this.refreshToken = newToken;
    }
}
