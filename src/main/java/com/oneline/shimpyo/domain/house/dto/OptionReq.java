package com.oneline.shimpyo.domain.house.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class OptionReq {
    private boolean wifi;
    private boolean pc;
    private boolean parking;
    private boolean bbq;
}
