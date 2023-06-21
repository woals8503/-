package com.oneline.shimpyo.controller;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.room.dto.PatchRoomReq;
import com.oneline.shimpyo.security.auth.CurrentMember;
import com.oneline.shimpyo.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/rooms")
public class RoomController {
    private final RoomService roomService;

    @PatchMapping("/{roomId}")
    public void updateRoom(@CurrentMember Member member, @PathVariable long roomId, @RequestPart PatchRoomReq patchRoomReq
                            , @RequestPart(value = "roomImages", required = false)List<MultipartFile> roomImages) {
        roomService.updateRoom(member, roomId, patchRoomReq, roomImages);

    }
}
