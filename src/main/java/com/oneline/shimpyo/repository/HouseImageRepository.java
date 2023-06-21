package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.house.HouseImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseImageRepository extends JpaRepository<HouseImage, Long> {
    List<HouseImage> findAllByHouseId(Long id);
}
