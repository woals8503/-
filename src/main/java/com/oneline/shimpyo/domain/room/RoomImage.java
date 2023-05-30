package com.oneline.shimpyo.domain.room;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "ROOM_IMAGE")
public class RoomImage {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @NotNull
    private String originalFileName;
    @NotNull
    private String savedFileName;
    @NotNull
    private String savedPath;
    @NotNull
    private String thumbnail;
}
