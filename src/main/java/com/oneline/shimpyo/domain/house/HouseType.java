package com.oneline.shimpyo.domain.house;

public enum HouseType {
    MOTEL("모텔"), HOTEL("호텔"), PENSION("펜션"), GUEST("게스트 하우스");

    private String type;

    HouseType(String type) {
        this.type = type;
    }
}
