package com.oneline.shimpyo.domain.member;

public enum MemberRole {
    ADMIN("관리자"), CLIENT("사용자");

    private String role;

    MemberRole(String role) {
        this.role = role;
    }
}
