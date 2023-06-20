package com.oneline.shimpyo.domain.house;

import com.oneline.shimpyo.domain.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "HOUSE_OPTIONS")
public class HouseOptions extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "option_id")
    private Long id;

    @NotNull
    @Column(name = "option_name")
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @Builder
    public HouseOptions(Long id, String name, House house) {
        this.id = id;
        this.name = name;
        this.house = house;
    }

}