package com.oneline.shimpyo.domain.pay;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.BaseResponseStatus;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum PayMethod {
    CARD("카드"), KAKAO("카카오 페이"),
    NAVER("네이버 페이"), PAYCO("페이코"),
    MOBILE("휴대폰 결제"), TOSS("토스 페이"), KGINICIS("KG 이니시스");

    private String method;

    PayMethod(String method) {
        this.method = method;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PayMethod findByMethod(String method) throws BaseException {
        return Stream.of(PayMethod.values())
                .filter(c -> c.name().equals(method))
                .findFirst()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.PAYMENT_METHOD_WRONG));
    }
}
