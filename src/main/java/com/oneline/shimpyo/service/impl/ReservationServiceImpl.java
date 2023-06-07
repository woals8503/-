package com.oneline.shimpyo.service.impl;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.MemberGrade;
import com.oneline.shimpyo.domain.reservation.dto.CouponReq;
import com.oneline.shimpyo.domain.reservation.dto.GetPrepareReservationReq;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.repository.dsl.MyCouponQuerydsl;
import com.oneline.shimpyo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.oneline.shimpyo.domain.BaseResponseStatus.MEMBER_NONEXISTENT;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final MemberRepository memberRepository;
    private final MyCouponQuerydsl myCouponQuerydsl;

    @Override
    public GetPrepareReservationReq prepareReservation(long memberId) throws BaseException {
        String uuid = UUID.randomUUID().toString();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(MEMBER_NONEXISTENT));
        MemberGrade memberGrade = member.getMemberGrade();
        List<CouponReq> myCouponList = myCouponQuerydsl.getMyCouponList(memberId);
        System.out.println("myCouponList = " + myCouponList);

        return new GetPrepareReservationReq(uuid, memberGrade.getGrade(),
                memberGrade.getDiscount(), myCouponList);
    }
}
