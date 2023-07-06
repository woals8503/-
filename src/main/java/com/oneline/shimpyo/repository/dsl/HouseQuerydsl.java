package com.oneline.shimpyo.repository.dsl;

import com.oneline.shimpyo.domain.house.dto.GetHouseListRes;
import com.oneline.shimpyo.domain.house.dto.QGetHouseListRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oneline.shimpyo.domain.house.QHouse.house;
import static com.oneline.shimpyo.domain.house.QHouseImage.houseImage;

@Repository
public class HouseQuerydsl {
    private final JPAQueryFactory jqf;

    public HouseQuerydsl(EntityManager em) {
        this.jqf = new JPAQueryFactory(em);
    }

    public List<GetHouseListRes> readHouseList(long memberId) {
        return jqf.select(new QGetHouseListRes(house.id, house.name, houseImage.savedURL, house.type))
                .from(house)
                .join(house.images, houseImage)
                .where(house.member.id.eq(memberId))
                .groupBy(house)
                .fetch();
    }
}
