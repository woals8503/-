package com.oneline.shimpyo.service.impl;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.coupon.Coupon;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.MemberGrade;
import com.oneline.shimpyo.domain.pay.PayMent;
import com.oneline.shimpyo.domain.pay.PayStatus;
import com.oneline.shimpyo.domain.reservation.dto.PostReservationReq;
import com.oneline.shimpyo.domain.room.Room;
import com.oneline.shimpyo.repository.CouponRepository;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.repository.PaymentRepository;
import com.oneline.shimpyo.repository.RoomRepository;
import com.oneline.shimpyo.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

import static com.oneline.shimpyo.domain.BaseResponseStatus.*;

@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private static final long EMPTY_ID = 0L;
    private static final String API_KEY = "6354023824364652";
    private static final String API_SECRET = "oCGC7lJGsgCzkJG6i9JyIKwU1MtNE5SBJ7GnkCVqVzxbqaLo3DxIY5CwUQAoq5kqxsCXo2iQS4v2oeu6";
    private final IamportClient iamportClient;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(RoomRepository roomRepository, MemberRepository memberRepository,
                              CouponRepository couponRepository, PaymentRepository paymentRepository) {
        this.iamportClient = new IamportClient(API_KEY, API_SECRET);
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    @Override
    public long createPayment(long memberId, PostReservationReq postReservationReq)
            throws IamportResponseException, IOException, BaseException {
        checkDuplicatePayment(postReservationReq);
        int paidPrice = validatePaid(memberId, postReservationReq);


        PayMent payment = PayMent.builder().impUid(postReservationReq.getImpUid()).UUID(postReservationReq.getMerchantUid())
                .payMethod(postReservationReq.getPayMethod()).price(paidPrice).payStatus(PayStatus.COMPLETE).build();
        paymentRepository.save(payment);

        return payment.getId();
    }

    private void checkDuplicatePayment(PostReservationReq postReservationReq) throws BaseException {
        Optional<PayMent> payment = paymentRepository
                .duplicateCheck(postReservationReq.getMerchantUid(), postReservationReq.getImpUid());

        if(payment.isPresent()){
            throw new BaseException(PAYMENT_DUPLICATE);
        }
    }

    private int validatePaid(long memberId, PostReservationReq postReservationReq)
            throws BaseException, IOException, IamportResponseException {
        Room room = roomRepository.findById(postReservationReq.getRoomId())
                .orElseThrow(() -> new BaseException(ROOM_NONEXISTENT));
        int paidPrice = getPaidPriceInPortone(postReservationReq.getImpUid());
        int priceToPay = room.getPrice();

        //회원 등급 체크
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(MEMBER_NONEXISTENT));
        MemberGrade memberGrade = member.getMemberGrade();
        priceToPay -= priceToPay / memberGrade.getDiscount();

        //쿠폰 체크
        if (postReservationReq.getCouponId() != EMPTY_ID) {
            Coupon coupon = couponRepository.findById(postReservationReq.getCouponId())
                    .orElseThrow(() -> new BaseException(COUPON_NONEXISTENT));
            priceToPay -= priceToPay * coupon.getDiscount();
        }

        //실제 결제 금액, db 금액 비교
        if (paidPrice != priceToPay) {
            throw new BaseException(WRONG_PAYMENT);
        }
        return paidPrice;
    }

    private int getPaidPriceInPortone(String impUid) throws IOException, IamportResponseException {
        Payment response = iamportClient.paymentByImpUid(impUid).getResponse();
        return (response.getAmount()).intValue();
    }

}
