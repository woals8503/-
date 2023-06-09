package com.oneline.shimpyo.domain;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */

    //reservation
    PAYMENT_METHOD_WRONG(false, 2010,"잘못된 결제 수단입니다."),
    PAYMENT_DUPLICATE(false, 2011, "중복된 결제입니다."),

    /**
     * 3000 : Response 오류
     */
    //member
    MEMBER_NONEXISTENT(false, 3010,"존재하지 않는 회원입니다."),

    //memberGrade
    MEMBER_GRADE_NONEXISTENT(false, 3020,"존재하지 않는 회원 등급입니다."),

    //coupon
    COUPON_NONEXISTENT(false, 3030,"존재하지 않는 쿠폰입니다."),

    //room
    ROOM_NONEXISTENT(false, 3040,"존재하지 않는 방입니다."),



    /**
     * 4000 : 기타 오류
     */
    WRONG_PAYMENT(false, 4000,"올바른 결제가 아닙니다."),
    PORTONE_EXCEPTION(false, 4001,"포트원 오류.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
