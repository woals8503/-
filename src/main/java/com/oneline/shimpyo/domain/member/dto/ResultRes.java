package com.oneline.shimpyo.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultRes<M> {
    private boolean isSuccess;
    private int code;
    private String message;

    public ResultRes(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
