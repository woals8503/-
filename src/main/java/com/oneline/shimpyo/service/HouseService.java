package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.house.House;
import com.oneline.shimpyo.domain.house.dto.HouseReq;
import com.oneline.shimpyo.domain.member.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface HouseService {

    long createHouse(Member member, HouseReq houseReq, List<MultipartFile> houseImages, List<MultipartFile> roomImages);
}
