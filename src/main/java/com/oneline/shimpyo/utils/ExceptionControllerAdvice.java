package com.oneline.shimpyo.utils;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.BaseResponseStatus;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.oneline.shimpyo.domain.BaseResponseStatus.PORTONE_EXCEPTION;

@Log4j2
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public BaseResponse<BaseResponseStatus> baseException(BaseException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder logMessage = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            logMessage.append(stackTrace[i]).append(", ");
        }
        log.error("BaseException Error : " + "Error status : " + e.getStatus().toString() + ", Path = " + logMessage);
        return new BaseResponse<>(e.getStatus());
    }

    @ExceptionHandler
    public BaseResponse<String> portOneException(IamportResponseException e) {
        log.error(e.getMessage());

        return new BaseResponse<>(PORTONE_EXCEPTION);
    }
}
