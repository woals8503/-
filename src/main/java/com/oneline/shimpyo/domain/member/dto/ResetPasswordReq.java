package com.oneline.shimpyo.domain.member.dto;

import lombok.Data;

@Data
public class ResetPasswordReq {
    private String phoneNumber;
    private String password;
}
