package com.oneline.shimpyo.domain;

import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;

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
    MEMBER_REGEX_WRONG(false, 3026, "회원 정보를 다시 입력해주세요."),
    EMAIL_DUPLICATE(false, 3027, "중복된 이메일입니다."),
    NICKNAME_DUPLICATE(false, 3028, "중복된 닉네임입니다."),
    VERIFICATION_CODE_SEND_EXCEPTION(false, 3029, "인증번호 전송에 실패하였습니다."),
    PHONE_NUMBER_DUPLICATE(false, 3030, "이미 등록된 휴대폰 번호입니다."),
    PASSWORD_REGEX_WRONG(false, 3031, "비밀번호를 다시 확인해주세요."),



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
    PORTONE_EXCEPTION(false, 4001,"포트원 오류."),
    // jwt
    JWT_TOKEN_NONEXISTENT(false, 4020, "JWT 토큰이 존재하지 않습니다."),

    //auth
    BAD_CREDENTIALS_EXCEPTION(false, 4040, "Provider - authenticate() : 비밀번호가 일치하지 않습니다."),

    // AWS S3
    AWS_S3_EXCEPTION(false, 4060, "AWS 연결 또는 서버에 오류가 발생했습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
