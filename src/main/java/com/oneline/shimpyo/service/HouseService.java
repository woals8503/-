package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.house.dto.GetHouseListRes;
import com.oneline.shimpyo.domain.house.dto.PatchHouseReq;
import com.oneline.shimpyo.domain.house.dto.PostHouseReq;
import com.oneline.shimpyo.domain.member.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HouseService {

    long createHouse(Member member, PostHouseReq houseReq, List<MultipartFile> houseImages, List<MultipartFile> roomImages);
    void updateHouse(Member member, long houseId, PatchHouseReq patchHouseReq, List<MultipartFile> houseImages);
    void deleteHouse(Member member, long houseId);

    List<GetHouseListRes> readHouseList(long memberId);
}
