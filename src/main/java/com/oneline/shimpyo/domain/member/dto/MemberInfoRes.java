package com.oneline.shimpyo.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class MemberInfoRes {
    String nickname;
    String email;
    String phoneNumber;
    Long userId;

    @QueryProjection
    public MemberInfoRes(String nickname, String email, String phoneNumber, Long userId) {
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }
}
