package com.oneline.shimpyo.domain.member;

import com.oneline.shimpyo.domain.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "MEMBER_IMAGE")
public class MemberImage extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_image_id")
    private Long id;

    @NotNull
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private String originalFileName;
    @NotNull
    private String savedFileName;
    @NotNull
    private String savedPath;
    @NotNull
    private boolean thumbnail;

}
