package com.oneline.shimpyo.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "MEMBER_GRADE")
public class MemberGrade {
    @Id @GeneratedValue
    @Column(name = "member_grade_id")
    private Long id;
    @NotNull
    private String grade;
    @NotNull
    private int discount;

    @OneToMany(mappedBy = "memberGrade")
    private List<Member> members = new ArrayList<>();
}
