package com.oneline.shimpyo.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class MemberProfileRes {
    String profileImage;
    String selfIntroduce;
    Long id;
    String nickname;

    @QueryProjection
    public MemberProfileRes(String profileImage, String selfIntroduce, Long id, String nickname) {
        this.profileImage = profileImage;
        this.selfIntroduce = selfIntroduce;
        this.id = id;
        this.nickname = nickname;
    }
}
