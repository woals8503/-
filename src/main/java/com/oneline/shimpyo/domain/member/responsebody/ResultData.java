package com.oneline.shimpyo.domain.member.responsebody;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultData<M> {
    private boolean isSuccess;
    private int code;
    private String message;
    private Object data;

    public ResultData(boolean isSuccess, int code, String message, Object data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
