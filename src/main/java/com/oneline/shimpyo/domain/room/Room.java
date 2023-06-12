package com.oneline.shimpyo.domain.room;

import com.oneline.shimpyo.domain.house.House;
import com.oneline.shimpyo.domain.reservation.NonMemberReservation;
import com.oneline.shimpyo.domain.reservation.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "ROOM")
public class Room {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int price;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @Embedded
    private RoomOption roomOption;

    @NotNull
    private int minPeople;

    @NotNull
    private int maxPeople;

    @NotNull
    //제공할 수 있는 총 객실 수
    private int totalCount;
    
    private LocalTime checkIn;
    private LocalTime checkOut;

    @OneToMany(mappedBy = "room", cascade = ALL)
    private List<RoomImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = ALL)
    private List<NonMemberReservation> nonMemberReservations = new ArrayList<>();

}
