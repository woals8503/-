package com.oneline.shimpyo.domain.house.dto;

import com.oneline.shimpyo.domain.house.ImageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatchImageReq {
    private int imageCount;
    private ImageStatus imageStatus;

}
