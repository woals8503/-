package com.oneline.shimpyo.domain.house;

import com.oneline.shimpyo.domain.review.Review;
import com.oneline.shimpyo.domain.room.Room;
import com.oneline.shimpyo.domain.wish.Wish;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "HOUSE")
public class House {

    @Id @GeneratedValue
    @Column(name = "house_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "house", cascade = ALL)
    private List<HouseOptions> houseOptions = new ArrayList<>();

    private int price;

    @OneToMany(mappedBy = "house", cascade = ALL)
    private List<HouseImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "house",cascade = ALL)
    private List<Room> rooms = new ArrayList<>();

    @OneToOne(mappedBy = "house")
    private HouseAddress houseAddress;

    private String contents;    // 사장님 한마디
//    private String houseInfo;   //  숙소 정보

    @OneToMany(mappedBy = "house", cascade = ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "house", cascade = ALL)
    private List<Wish> wishList = new ArrayList<>();

    @Enumerated(value = STRING)
    private HouseType type;

//    private MapLocation location;

}
