package com.oneline.shimpyo.domain.member;

import lombok.Getter;

@Getter
public enum GradeName {
    SILVER("실버"), GOLD("골드"), DIA("다이아");

    private String rank;

    GradeName(String rank) {
        this.rank = rank;
    }
}
