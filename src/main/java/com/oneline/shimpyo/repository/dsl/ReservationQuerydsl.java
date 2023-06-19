package com.oneline.shimpyo.repository.dsl;

import com.oneline.shimpyo.domain.reservation.dto.GetReservationListRes;
import com.oneline.shimpyo.domain.reservation.dto.QGetReservationListRes;
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
import static com.oneline.shimpyo.domain.reservation.QReservation.reservation;

@Repository
public class ReservationQuerydsl {
    private final JPAQueryFactory jqf;

    public ReservationQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }

    public Page<GetReservationListRes> readReservationList(long memberId, Pageable pageable) {
        List<GetReservationListRes> reservationList = jqf.select(new QGetReservationListRes(reservation.id, houseImage.savedPath, houseImage.savedFileName, house.name,
                        house.member.nickname, house.type, reservation.checkInDate, reservation.checkOutDate,
                        house.name, reservation.reservationStatus))
                .from(reservation)
                .join(reservation.room.house, house)
                .join(house.images, houseImage)
                .where(reservation.member.id.eq(memberId))
                .orderBy(reservation.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> where = jqf.select(reservation.count())
                .from(reservation)
                .where(reservation.member.id.eq(memberId));

        return PageableExecutionUtils.getPage(reservationList, pageable, where::fetchOne);
    }
}
