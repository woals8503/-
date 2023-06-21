package com.oneline.shimpyo.repository;

import com.oneline.shimpyo.domain.house.House;
import com.oneline.shimpyo.domain.reservation.dto.HostHouseReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {

    Optional<House> findTopByMemberId(Long member_id);
    @Query("select new com.oneline.shimpyo.domain.reservation.dto.HostHouseReq(h.id, h.name, hi.savedURL)" +
            " from House h join HouseImage hi on hi.house.id = h.id where h.member.id = :member_id group by h")
    List<HostHouseReq> findAllByMemberId(@Param("member_id") Long member_id);
}
