package com.oneline.shimpyo.domain.member.responsebody;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<M> {
    private boolean isSuccess;
    private int code;
    private String message;

    public Result(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
