package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.coupon.Coupon;
import com.oneline.shimpyo.domain.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
