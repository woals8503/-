package com.oneline.shimpyo.domain.pay;

import lombok.Getter;

@Getter
public enum PayMethod {
    CARD("카드"), KAKAO("카카오 페이"), 
    NAVER("네이버 페이"), PAYCO("페이코"), 
    MOBILE("휴대폰 결제"), TOSS("토스 페이");

    private String method;

    PayMethod(String method) {
        this.method = method;
    }
}
