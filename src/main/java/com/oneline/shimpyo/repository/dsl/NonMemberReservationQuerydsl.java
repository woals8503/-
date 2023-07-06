package com.oneline.shimpyo.repository.dsl;

import com.oneline.shimpyo.domain.reservation.dto.GetReservationRes;
import com.oneline.shimpyo.domain.reservation.dto.QGetReservationRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.oneline.shimpyo.domain.house.QHouse.house;
import static com.oneline.shimpyo.domain.house.QHouseAddress.houseAddress;
import static com.oneline.shimpyo.domain.pay.QPayMent.payMent;
import static com.oneline.shimpyo.domain.reservation.QNonMemberReservation.nonMemberReservation;
import static com.oneline.shimpyo.domain.room.QRoom.room;

@Repository
public class NonMemberReservationQuerydsl {
    private final JPAQueryFactory jqf;

    public NonMemberReservationQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }

    public GetReservationRes readReservation(long reservationId) {
        return jqf.select(new QGetReservationRes(house.id, nonMemberReservation.id, nonMemberReservation.reservationStatus,
                        house.member.id, house.name,
                        house.member.nickname, nonMemberReservation.checkInDate, nonMemberReservation.checkOutDate,
                        room.checkIn, room.checkOut, room.id, room.name, room.maxPeople, room.minPeople,
                        nonMemberReservation.peopleCount, houseAddress.lat, houseAddress.lng, houseAddress.fullAddress,
                        payMent.price, payMent.remainPrice))
                .from(nonMemberReservation)
                .join(nonMemberReservation.room, room)
                .join(room.house, house)
                .join(house.houseAddress, houseAddress)
                .join(nonMemberReservation.payMent, payMent)
                .where(nonMemberReservation.id.eq(reservationId))
                .fetchFirst();

    }

}
