package com.oneline.shimpyo.domain.house;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.oneline.shimpyo.domain.BaseException;

import java.util.stream.Stream;

import static com.oneline.shimpyo.domain.BaseResponseStatus.HOUSE_TYPE_WRONG;

public enum HouseType {
    MOTEL("모텔"), HOTEL("호텔"), PENSION("펜션"), GUEST("게스트 하우스");

    private String type;

    HouseType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static HouseType findByMethod(String type) throws BaseException {
        return Stream.of(HouseType.values())
                .filter(c -> c.name().equals(type))
                .findFirst()
                .orElseThrow(() -> new BaseException(HOUSE_TYPE_WRONG));
    }
}
