package com.oneline.shimpyo.repository.dsl;

import com.oneline.shimpyo.domain.house.HouseType;
import com.oneline.shimpyo.domain.house.dto.*;
import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import com.oneline.shimpyo.domain.review.ReviewRating;
import com.oneline.shimpyo.domain.room.Room;

import com.oneline.shimpyo.domain.room.dto.QRoomInfo;
import com.oneline.shimpyo.domain.room.dto.RoomInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.oneline.shimpyo.domain.house.QHouse.house;
import static com.oneline.shimpyo.domain.house.QHouseAddress.houseAddress;
import static com.oneline.shimpyo.domain.house.QHouseImage.houseImage;
import static com.oneline.shimpyo.domain.house.QHouseOptions.houseOptions;
import static com.oneline.shimpyo.domain.reservation.QReservation.reservation;
import static com.oneline.shimpyo.domain.review.QReview.review;
import static com.oneline.shimpyo.domain.room.QRoom.room;
import static com.oneline.shimpyo.domain.room.QRoomImage.roomImage;

@Repository
public class HouseQuerydsl {
    private final JPAQueryFactory jqf;

    public HouseQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }

    public List<GetMyHouseListRes> readMyHouseList(long memberId) {
        return jqf.select(new QGetMyHouseListRes(house.id, house.name, houseImage.savedURL, house.type))
                .from(house)
                .join(house.images, houseImage)
                .where(house.member.id.eq(memberId))
                .groupBy(house)
                .fetch();
    }

    public HouseInfo findHouseAndAddressByHouseId(long houseId) {
        return jqf.select(new QHouseInfo(house.id, house.name, house.type, house.contents, houseAddress.postCode
                                                        , houseAddress.sido, houseAddress.sigungu, houseAddress.fullAddress, houseAddress.lat, houseAddress.lng))
                .from(house)
                .join(house.houseAddress, houseAddress)
                .on(house.id.eq(houseAddress.house.id))
                .where(house.id.eq(houseId))
                .fetchOne();
    }

    public List<String> findHouseImagesByHouseId(long houseId) {
        return jqf.select(houseImage.savedURL)
                .from(houseImage)
                .where(houseImage.house.id.eq(houseId))
                .fetch();
    }

    public List<String> findHouseOptionsByHouseId(long houseId) {
        return jqf.select(houseOptions.name)
                .from(houseOptions)
                .where(houseOptions.house.id.eq(houseId))
                .fetch();
    }

    public List<RoomInfo> findRoomsByHouseId(long houseId) {
        return jqf.select(new QRoomInfo(room.id, room.name, room.price, room.minPeople, room.maxPeople, room.bedCount, room.bedroomCount
                                    , room.bathroomCount, room.totalCount, room.checkIn, room.checkOut))
                .from(room)
                .where(room.house.id.eq(houseId))
                .fetch();
    }

    public List<String> findRoomImagesByRoomId(long roomId) {
        return jqf.select(roomImage.savedURL)
                .from(roomImage)
                .where(roomImage.room.id.eq(roomId))
                .fetch();
    }

    public List<GetHouseListRes> findAllHouse(Pageable pageable, SearchFilterReq searchFilter) {
        List<GetHouseListRes> foundHouseList = jqf.select(new QGetHouseListRes(house.id, house.name, house.type, room.price.min(), house.contents, room.id.min()))
                .from(house)
                .join(house.houseAddress, houseAddress)
                .on(house.id.eq(houseAddress.house.id))
                .join(house.rooms, room)
                .on(house.id.eq(room.house.id))
                .where(minPeopleLoe(searchFilter.getPeople()), maxPeopleGoe(searchFilter.getPeople()), typeEq(searchFilter.getType())
                        , cityEq(searchFilter.getCity()), districtEq(searchFilter.getDistrict()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(house.id)
                .fetch();


        // 각 객실 평점 및 이미지 저장
        for (GetHouseListRes houseRes : foundHouseList) {
            // 평점 저장
            double result = 0;
            long totalReviewCount = jqf.select(review.count())
                    .from(review)
                    .where(review.house.id.eq(houseRes.getHouseId()))
                    .fetchOne();
            if (totalReviewCount > 0) { // 리뷰 존재 시에만 해당 로직 수행
                long goodReviewCount = jqf.select(review.count())
                        .from(review)
                        .where(review.house.id.eq(houseRes.getHouseId()), review.reviewRating.eq(ReviewRating.GOOD))
                        .fetchOne();
                result = ((double) goodReviewCount / (double) totalReviewCount) * 100.0;
            }
            houseRes.setRatio(result);

            // 이미지 저장
            houseRes.setHouseImages(findHouseImagesByHouseId(houseRes.getHouseId()));
        }

        // 체크인, 체크아웃 기간이 명시되어 있을 경우
        if (searchFilter.getCheckin() != null && searchFilter.getCheckout() != null) {
            for (GetHouseListRes houseRes : foundHouseList) {
                Room foundRoom = jqf.selectFrom(room)
                        .where(room.id.eq(houseRes.getRoomId()))
                        .fetchOne();
                long reservedCount = jqf.select(reservation.count())
                        .from(reservation)
                        .where(reservation.reservationStatus.eq(ReservationStatus.COMPLETE), reservation.room.id.eq(houseRes.getRoomId()),
                                reservation.checkInDate.loe(searchFilter.getCheckin()),
                                reservation.checkOutDate.goe(searchFilter.getCheckout()))
                        .fetchOne();
                if (foundRoom.getTotalCount() <= reservedCount) { // 얘약인원이 꽉 찬 경우 해당 숙소 정보를 목록에서 제외
                    houseRes.setSoldout(true);
                }
            }
        }

        Comparator<GetHouseListRes> resOrderByRating = Comparator.comparing(GetHouseListRes::getRatio).reversed();
        Collections.sort(foundHouseList, resOrderByRating);

        return foundHouseList;
    }

    // 동적 Query DSL Where 조건
    private BooleanExpression minPeopleLoe(int minPeople) {
        return minPeople != 0 ? room.minPeople.loe(minPeople) : null;
    }
    private BooleanExpression maxPeopleGoe(int maxPeople) {
        return maxPeople != 0 ? room.maxPeople.goe(maxPeople) : null;
    }
    private BooleanExpression typeEq(HouseType type) {
        return type != null ? house.type.eq(type) : null;
    }
    private BooleanExpression cityEq(String city) {
        return city != null ? houseAddress.sido.eq(city) : null;
    }
    private BooleanExpression districtEq(String district) {
        return district != null ? houseAddress.sigungu.eq(district) : null;
    }
}
