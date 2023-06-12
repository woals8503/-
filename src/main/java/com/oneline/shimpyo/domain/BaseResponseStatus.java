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
    //common 2000

    //member 2020

    //house 2040
    HOUSE_TYPE_WRONG(false, 2041,"잘못된 숙소 종류입니다."),

    //room 2060

    //review 2080

    //reservation 2100
    PAYMENT_METHOD_WRONG(false, 2110,"잘못된 결제 수단입니다."),

    //coupon 2120


    /**
     * 3000 : Response 오류
     */
    //common 3000

    //member 3020
    MEMBER_NONEXISTENT(false, 3020,"존재하지 않는 회원입니다."),
    MEMBER_GRADE_NONEXISTENT(false, 3025,"존재하지 않는 회원 등급입니다."),

    //house 3040
    HOUSE_NONEXISTENT(false, 3040,"존재하지 않는 숙소입니다."),

    //room 3060
    ROOM_NONEXISTENT(false, 3060,"존재하지 않는 방입니다."),

    //review 3080

    //reservation 3100
    RESERVATION_NONEXISTENT(false, 3100,"존재하지 않는 예약입니다."),
    RESERVATION_CANCEL(false, 3101,"취소된 예약입니다."),
    PAYMENT_DUPLICATE(false, 3102, "중복된 결제입니다."),
    PAYMENT_WRONG(false, 3103,"올바른 결제가 아닙니다."),
    REFUND_WRONG(false, 3104, "환불 가능 금액보다 환불 요구 금액이 더 큽니다."),

    //coupon 3120
    COUPON_NONEXISTENT(false, 3120,"존재하지 않는 쿠폰입니다."),


    /**
     * 4000 : 기타 오류
     */
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
