package com.oneline.shimpyo.domain.member.dto;

import lombok.Data;

@Data
public class ResetPasswordReq {
    private String firstPassword;
    private String secondPassword;
    private String phoneNumber;
}
