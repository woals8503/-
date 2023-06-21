package com.oneline.shimpyo.service;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.room.dto.PatchRoomReq;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomService {
    void updateRoom(Member member, long roomId, PatchRoomReq patchRoomReq, List<MultipartFile> roomImages);
}
