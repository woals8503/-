package com.oneline.shimpyo.domain.house;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "HOUSE_ADDRESS")
public class HouseAddress {

    @Id @GeneratedValue
    @Column(name = "house_address")
    private Long id;

    @NotNull
    private String sido;    // 시/도-서울특별시 or 경기도 or 부산광역시
    @NotNull
    private String gungu;   //
    @NotNull
    private String postCode; //
    @NotNull
    private String sigungu; // 마포구, 서대문구, 해운대구, 분당구
    @NotNull
    private String address;
//    @NotNull
//    private String doro;  // 어디 몇동 몇호
//    @NotNull
//    private String dong;

    @NotNull
    private double lat; // 위도
    @NotNull
    private double lng; // 경도

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;
}
