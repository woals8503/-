package com.oneline.shimpyo.domain.pay;

import lombok.Getter;

@Getter
public enum PayStatus {
    CANCEL("결제 취소"), COMPLETE("결제 완료"), WAITING("결제 대기");

    private String status;

    PayStatus(String status) {
        this.status = status;
    }
}
