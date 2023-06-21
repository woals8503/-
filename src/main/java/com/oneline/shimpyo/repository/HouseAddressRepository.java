package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.house.HouseAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseAddressRepository extends JpaRepository<HouseAddress, Long> {
    Optional<HouseAddress> findByHouseId(Long id);
}
