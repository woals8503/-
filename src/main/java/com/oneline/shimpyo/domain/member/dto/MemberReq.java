package com.oneline.shimpyo.domain.member.dto;

import lombok.Data;

@Data
public class MemberReq {
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
}
