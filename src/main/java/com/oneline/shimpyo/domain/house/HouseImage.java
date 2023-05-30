package com.oneline.shimpyo.domain.house;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "HOUSE_IMAGE")
public class HouseImage {
    @Id @GeneratedValue
    @Column(name = "houseImage_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @NotNull
    private String originalFileName;
    @NotNull
    private String savedFileName;
    @NotNull
    private String savedPath;
    @NotNull
    private boolean thumbnail;
}
