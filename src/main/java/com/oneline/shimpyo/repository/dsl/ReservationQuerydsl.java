package com.oneline.shimpyo.repository.dsl;

import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import com.oneline.shimpyo.domain.reservation.dto.GetReservationListRes;
import com.oneline.shimpyo.domain.reservation.dto.GetReservationRes;
import com.oneline.shimpyo.domain.reservation.dto.QGetReservationListRes;
import com.oneline.shimpyo.domain.reservation.dto.QGetReservationRes;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oneline.shimpyo.domain.house.QHouse.house;
import static com.oneline.shimpyo.domain.house.QHouseImage.houseImage;
import static com.oneline.shimpyo.domain.pay.QPayMent.payMent;
import static com.oneline.shimpyo.domain.reservation.QReservation.reservation;
import static com.oneline.shimpyo.domain.room.QRoom.room;

@Repository
public class ReservationQuerydsl {
    private final JPAQueryFactory jqf;
    private final static int FULL_REFUND = 0;

    public ReservationQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }

    public Page<GetReservationListRes> readReservationList(long memberId, Pageable pageable) {
        List<GetReservationListRes> reservationList = jqf.select(new QGetReservationListRes(reservation.id,
                        houseImage.savedURL,
                        house.name, house.member.nickname, house.type, reservation.checkInDate,
                        reservation.checkOutDate, house.name, reservation.reservationStatus))
                .from(reservation)
                .join(reservation.room.house, house)
                .join(house.images, houseImage)
                .groupBy(reservation)
                .where(reservation.member.id.eq(memberId))
                .where(reservation.reservationStatus.ne(ReservationStatus.CANCEL)
                        .or(reservation.payMent.remainPrice.ne(FULL_REFUND)))
                .orderBy(reservation.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> where = jqf.select(reservation.count())
                .from(reservation)
                .where(reservation.member.id.eq(memberId));

        return PageableExecutionUtils.getPage(reservationList, pageable, where::fetchOne);
    }

    public GetReservationRes readReservation(long reservationId) {
        return jqf.select(new QGetReservationRes(house.id, reservation.id, reservation.reservationStatus, house.name,
                        house.member.nickname, reservation.checkInDate, reservation.checkOutDate,
                        room.checkIn, room.checkOut, room.id, room.name,
                        reservation.peopleCount, house.name, payMent.price, payMent.remainPrice))
                .from(reservation)
                .join(reservation.room, room)
                .join(reservation.room.house, house)
                .join(reservation.payMent, payMent)
                .where(reservation.id.eq(reservationId))
                .fetchFirst();
    }
}
