package com.oneline.shimpyo.domain.room;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
public class RoomOption {

    private int bedCount;

    private int bedroomCount;

    private int bathroomCount;

    private int pcCount;
}
