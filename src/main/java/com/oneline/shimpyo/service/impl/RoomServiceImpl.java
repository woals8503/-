package com.oneline.shimpyo.service.impl;

import com.oneline.shimpyo.domain.BaseException;
import com.oneline.shimpyo.domain.house.House;
import com.oneline.shimpyo.domain.house.ImageStatus;
import com.oneline.shimpyo.domain.house.dto.FileReq;
import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.room.Room;
import com.oneline.shimpyo.domain.room.RoomImage;
import com.oneline.shimpyo.domain.room.dto.PatchRoomReq;
import com.oneline.shimpyo.modules.S3FileHandler;
import com.oneline.shimpyo.repository.HouseRepository;
import com.oneline.shimpyo.repository.RoomImageRepository;
import com.oneline.shimpyo.repository.RoomRepository;
import com.oneline.shimpyo.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.oneline.shimpyo.domain.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {
    private final RoomImageRepository roomImageRepository;
    private final RoomRepository roomRepository;
    private final S3FileHandler s3FileHandler;

    @Override
    @Transactional
    public void updateRoom(Member member, long roomId, PatchRoomReq patchRoomReq, List<MultipartFile> roomImages) {
        Room foundRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(ROOM_NONEXISTENT));
        if (foundRoom.getHouse().getMember().getId() != member.getId()) throw new BaseException(ROOM_MEMBER_WRONG);

        // 객실 기본 정보 수정
        foundRoom.setName(patchRoomReq.getName());
        foundRoom.setPrice(patchRoomReq.getPrice());
        foundRoom.setMinPeople(patchRoomReq.getMinPeople());
        foundRoom.setMaxPeople(patchRoomReq.getMaxPeople());
        foundRoom.setBedCount(patchRoomReq.getBedCount());
        foundRoom.setBedroomCount(patchRoomReq.getBedroomCount());
        foundRoom.setBathroomCount(patchRoomReq.getBathroomCount());
        foundRoom.setTotalCount(patchRoomReq.getTotalCount());
        foundRoom.setCheckIn(patchRoomReq.getCheckIn());
        foundRoom.setCheckOut(patchRoomReq.getCheckOut());
        
        // 객실 이미지 수정
        if (patchRoomReq.getPatchImageReqs() != null) {
            int roomImageIdx = 0;
            for (int i = 0; i < patchRoomReq.getPatchImageReqs().size(); i++) {
                int imageCount = patchRoomReq.getPatchImageReqs().get(i).getImageCount();
                ImageStatus imageStatus = patchRoomReq.getPatchImageReqs().get(i).getImageStatus();
                if (imageStatus.equals(ImageStatus.ADD)) {
                    List<RoomImage> foundImages = roomImageRepository.findAllByRoomId(foundRoom.getId());
                    if (foundImages.size() == 5) throw new BaseException(IMAGE_STATUS_FULL);
                    Optional<FileReq> uploadedFile = s3FileHandler.uploadFile(roomImages.get(roomImageIdx));
                    if (uploadedFile.isPresent()) {
                        RoomImage toSaveRoomImage = RoomImage.builder()
                                .room(foundRoom)
                                .originalFileName(uploadedFile.get().getOriginalFileName())
                                .savedURL(uploadedFile.get().getSavedURL())
                                .build();
                        roomImageRepository.save(toSaveRoomImage);
                        roomImageIdx++;
                    }
                } else if (imageStatus.equals(ImageStatus.MODIFY)) {
                    Optional<FileReq> uploadedFile = s3FileHandler.uploadFile(roomImages.get(roomImageIdx));
                    if (uploadedFile.isPresent()) {
                        List<RoomImage> foundImages = roomImageRepository.findAllByRoomId(foundRoom.getId());
                        // S3 버킷 기존 파일 삭제
                        s3FileHandler.removeFile(foundImages.get(imageCount).getSavedURL());
                        // 업로드된 수정 파일 정보로 업데이트
                        foundImages.get(imageCount).setOriginalFileName(uploadedFile.get().getOriginalFileName());
                        foundImages.get(imageCount).setSavedURL(uploadedFile.get().getSavedURL());
                        roomImageIdx++;
                    }
                } else {
                    List<RoomImage> foundImages = roomImageRepository.findAllByRoomId(foundRoom.getId());
                    // S3 버킷 기존 파일 삭제
                    s3FileHandler.removeFile(foundImages.get(imageCount).getSavedURL());
                    // DB 삭제
                    roomImageRepository.deleteById(foundImages.get(imageCount).getId());
                }
            }
        }
    }

    @Override
    @Transactional
    public void deleteRoom(Member member, long roomId) {
        Room foundRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(ROOM_NONEXISTENT));
        if (member.getId() != foundRoom.getHouse().getMember().getId()) throw new BaseException(ROOM_MEMBER_WRONG);
        List<RoomImage> roomImages = roomImageRepository.findAllByRoomId(foundRoom.getId());
        for (int i = 0; i < roomImages.size(); i++) {
            s3FileHandler.removeFile(roomImages.get(i).getSavedURL());
        }
        roomRepository.deleteById(roomId);
    }
}
