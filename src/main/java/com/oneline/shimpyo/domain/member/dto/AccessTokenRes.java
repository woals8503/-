package com.oneline.shimpyo.domain.member.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AccessTokenRes {
    Map<String, String> responseMap;
}
