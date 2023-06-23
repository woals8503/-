package com.oneline.shimpyo.modules;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.member.Member;
import org.springframework.stereotype.Component;

import static com.oneline.shimpyo.domain.BaseResponseStatus.INVALID_MEMBER;
import static com.oneline.shimpyo.domain.BaseResponseStatus.NON_MEMBER;

@Component
public class CheckMember {

    private static final long NON_MEMBER_ID = 0L;

    public long getMemberId(Member member, boolean throwException) {
        if (throwException) {
            if (member == null) {
                throw new BaseException(NON_MEMBER);
            }
            return member.getId();
        }
        if (member == null) {
            return NON_MEMBER_ID;
        }
        return member.getId();
    }

    public void checkCurrentMember(Member member, long id) {
        if (member == null) {
            throw new BaseException(NON_MEMBER);
        }
        if (member.getId() != id) {
            throw new BaseException(INVALID_MEMBER);
        }
    }

}
