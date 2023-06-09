package com.oneline.shimpyo.service.impl;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.MemberGrade;
import com.oneline.shimpyo.domain.pay.PayMent;
import com.oneline.shimpyo.domain.reservation.Reservation;
import com.oneline.shimpyo.domain.reservation.ReservationStatus;
import com.oneline.shimpyo.domain.reservation.dto.CouponReq;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationReq;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.oneline.shimpyo.domain.room.Room;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.repository.PaymentRepository;
import com.oneline.shimpyo.repository.ReservationRepository;
import com.oneline.shimpyo.repository.RoomRepository;
import com.oneline.shimpyo.repository.dsl.MyCouponQuerydsl;
import com.oneline.shimpyo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.oneline.shimpyo.domain.BaseResponseStatus.MEMBER_NONEXISTENT;
import static com.oneline.shimpyo.domain.BaseResponseStatus.ROOM_NONEXISTENT;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final PaymentRepository paymentRepository;
    private final MyCouponQuerydsl myCouponQuerydsl;

    @Override
    public GetPrepareReservationReq prepareReservation(long memberId) throws BaseException {
        String uuid = UUID.randomUUID().toString();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(MEMBER_NONEXISTENT));
        MemberGrade memberGrade = member.getMemberGrade();
        List<CouponReq> myCouponList = myCouponQuerydsl.getMyCouponList(memberId);

        return new GetPrepareReservationReq(uuid, memberGrade.getGrade(), memberGrade.getDiscount(), myCouponList);
    }

    @Transactional
    @Override
    public long createReservation(long memberId, long paymentId, PostReservationReq postReservationReq) throws BaseException {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(MEMBER_NONEXISTENT));
        Room room = roomRepository.findById(postReservationReq.getRoomId())
                .orElseThrow(() -> new BaseException(ROOM_NONEXISTENT));
        PayMent payment = paymentRepository.findById(paymentId).orElseThrow(() -> new BaseException(ROOM_NONEXISTENT));

        //todo check in, out date 고치기
        Reservation reservation = Reservation.builder().room(room).member(member).payMent(payment)
                .peopleCount(postReservationReq.getPeopleCount()).phoneNumber(postReservationReq.getPhoneNumber())
                .reservationStatus(ReservationStatus.COMPLETE).checkInDate(LocalDateTime.now())
                .checkOutDate(LocalDateTime.now()).build();
        reservationRepository.save(reservation);

        return reservation.getId();
    }

}
