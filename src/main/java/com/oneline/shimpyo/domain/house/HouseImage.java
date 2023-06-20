package com.oneline.shimpyo.domain.house;

import com.oneline.shimpyo.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "HOUSE_IMAGE")
public class HouseImage extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @NotNull
    private String originalFileName;
    @NotNull
    @Column(name = "saved_url")
    private String savedURL;

    @Builder
    public HouseImage(Long id, House house, String originalFileName, String savedURL) {
        this.id = id;
        this.house = house;
        this.originalFileName = originalFileName;
        this.savedURL = savedURL;
    }
}
