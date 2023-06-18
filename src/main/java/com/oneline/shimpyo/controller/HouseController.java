package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.BaseResponse;
import com.oneline.shimpyo.domain.house.dto.HouseReq;
import com.oneline.shimpyo.domain.house.dto.HouseRegisterRes;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @PostMapping("/public/houses")
    public BaseResponse<HouseRegisterRes> createHouse(@RequestPart HouseReq houseReq, @RequestPart List<MultipartFile> houseImages
                                , @RequestPart List<MultipartFile> roomImages) {
        // Member 테스트용
        Member member = Member.builder()
                .id(1L)
                .build();

        long houseId = houseService.createHouse(member, houseReq, houseImages, roomImages);

        return new BaseResponse<>(new HouseRegisterRes(houseId));
    }

}
