package com.oneline.shimpyo.domain.member.dto;

import lombok.Data;

@Data
public class ChangePasswordReq {
    private String currentPassword;
    private String modifiedPassword;
}
