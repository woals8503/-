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

    //member
    MEMBER_NONEXISTENT(false, 3010,"존재하지 않는 회원입니다."),

    //memberGrade
    MEMBERGRADE_NONEXISTENT(false, 3020,"존재하지 않는 회원입니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
