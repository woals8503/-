package com.oneline.shimpyo.domain.review;

import lombok.Getter;

@Getter
public enum ReviewRating {
    GOOD("좋아요"), NORMAL("보통이에요"), BAD("별로에요");

    private String status;

    ReviewRating(String status) {
        this.status = status;
    }
}
