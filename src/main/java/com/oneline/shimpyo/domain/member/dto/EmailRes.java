package com.oneline.shimpyo.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRes<M> {
    private boolean isSuccess;
    private int code;
    private String message;
    private Object data;

    public EmailRes(boolean isSuccess, int code, String message, Object data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
